package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatMessageResponseDTO;
import com.ssafy.forestkeeper.domain.dao.chat.ChatMessage;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.repository.chat.ChatMessageRepository;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomRepository;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomUserRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.ChatRoomNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import com.ssafy.forestkeeper.util.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listener
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    // 채팅 방에 입장한 클라이언트의 sessionId 와 채팅방 ID 를 매핑한 정보 저장
    public static final String ENTER_INFO = "ENTER_INFO";
    private final RedisTemplate<String, ChatMessage> redisChatMessageTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 Redis Topic 정보. 서버별로 채팅방에 매치되는 topic 정보를 Map 에 넣어 roomId 로 찾을 수 있도록 함
    private Map<String, ChannelTopic> topics;
    private HashOperations<String, String, String> hashOpsEnterInfo;

    @PostConstruct
    private void init() {

        opsHashChatRoom = redisChatMessageTemplate.opsForHash();
        hashOpsEnterInfo = redisChatMessageTemplate.opsForHash();

        topics = new HashMap<>();

    }

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash 에 저장
    @Override
    public void createChatRoom(ChatRoom chatRoom) {

        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getId(), chatRoom);

    }

    // 채팅방 입장 : redis 에 topic 을 만들고 pub/sub 통신을 하기 위해 리스너 설정
    @Override
    public void enterChatRoom(String roomId) {

        ChannelTopic topic = topics.get(roomId);

        if (topic == null) topic = new ChannelTopic(roomId);

        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);

        topics.put(roomId, topic);

    }

    @Override
    public ChannelTopic getTopic(String roomId) {

        return topics.get(roomId);

    }

    @Override
    public List<ChatMessageDTO> getChatMessageValue(String key) {

        List<ChatMessage> chatMessageList = redisChatMessageTemplate.opsForList().range(key, 0, -1);

        List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();

        chatMessageList.forEach(chatMessage ->
                chatMessageDTOList.add(ChatMessageDTO.builder()
                        .roomId(chatMessage.getChatRoom().getId())
                        .index(chatMessage.getIndex())
                        .userId(chatMessage.getUser().getId())
                        .nickname(chatMessage.getUser().getNickname())
                        .content(chatMessage.getContent())
                        .sendTime(chatMessage.getSendTime())
                        .delete(chatMessage.isDelete())
                        .build()
                )
        );

        return chatMessageDTOList;

    }

    @Override
    public void setChatMessageValue(String key, ChatMessageDTO value) {

        ChatRoom chatRoom = chatRoomRepository.findByIdAndDelete(value.getRoomId(), false)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다."));
        ChatMessage chatMessage;

        switch (value.getMessageCode()) {
            case TALK:
                long index = redisChatMessageTemplate.opsForList().size(key);

                chatMessage = ChatMessage.builder()
                        .chatRoom(chatRoom)
                        .index(index)
                        .user(userRepository.findByNicknameAndDelete(value.getNickname(), false)
                                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 존재하지 않습니다.")))
                        .content(value.getContent())
                        .sendTime(value.getSendTime())
                        .build();

                redisChatMessageTemplate.opsForList().rightPush(key, chatMessage);

                chatMessageRepository.save(chatMessage);

                break;
            case DELETE:
                chatMessage = redisChatMessageTemplate.opsForList().index(key, value.getIndex());

                chatMessage.changeDelete();

                redisChatMessageTemplate.opsForList().set(key, value.getIndex(), chatMessage);

                chatMessageRepository.save(chatMessage);

                break;
        }

    }

    // 유저가 입장한 채팅방 ID 와 유저 세션 ID 맵핑 정보 저장
    @Override
    public void setUserEnterInfo(String sessionId, String roomId) {

        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);

    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    @Override
    public String getUserEnterRoomId(String sessionId) {

        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);

    }

    // 유저 세션 정보와 맵핑된 채팅방 ID 삭제
    @Override
    public void removeUserEnterInfo(String sessionId) {

        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);

    }

    @Override
    public ChatMessageResponseDTO getLastChatMessageValue(String key) {

        ChatMessage chatMessage = redisChatMessageTemplate.opsForList().index(key, -1);

        if (chatMessage == null) return null;

        return ChatMessageResponseDTO.builder()
                .userId(chatMessage.getUser().getId())
                .nickname(chatMessage.getUser().getNickname())
                .content(chatMessage.getContent())
                .sendTime(chatMessage.getSendTime())
                .delete(chatMessage.isDelete())
                .build();

    }

}
