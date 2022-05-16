package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.*;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoomUser;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomRepository;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomUserRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.ChatRoomNotFoundException;
import com.ssafy.forestkeeper.exception.ChatRoomUserNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final ChatMessageService chatMessageService;

    @Transactional
    @Override
    public void createChatRoom(ChatRoomRegisterRequestDTO chatRoomRegisterRequestDTO) {

        // 내 정보
        User me = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        // 채팅방 이름
        String name = chatRoomRegisterRequestDTO.getName();

        // 채팅방 참여 인원 ID 목록
        List<String> userList = chatRoomRegisterRequestDTO.getUserIdList();

        // 내 ID 추가
        userList.add(me.getId());

        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .userCount(userList.size())
                .build();

        chatRoomRepository.save(chatRoom);

        chatMessageService.createChatRoom(chatRoom);

        for (String userId : userList) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

            chatRoomUserRepository.save(
                    ChatRoomUser.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .build()
            );
        }

    }

    @Override
    public ChatRoomResponseDTO enterChatRoom(String roomId) {

        chatMessageService.enterChatRoom(roomId);

        ChatRoom chatRoom = chatRoomRepository.findByIdAndDelete(roomId, false)
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방 정보가 존재하지 않습니다."));

        // 채팅방 전체 메시지
        List<ChatMessageDTO> chatMessageDTOList = chatMessageService.getChatMessageValue(roomId);

        // 채팅방 참여 인원
        List<User> userList = chatRoomUserRepository.findUserByRoomIdAndDelete(roomId, false)
                .orElseThrow(() -> new UserNotFoundException("채팅방 내 회원 정보가 존재하지 않습니다."));

        System.out.println("userList: " + userList);

        List<ChatRoomUserResponseDTO> chatRoomUserResponseDTOList = new ArrayList<>();

        userList.forEach(user -> chatRoomUserResponseDTOList.add(
                        ChatRoomUserResponseDTO.builder()
                                .userId(user.getId())
                                .nickname(user.getNickname())
                                .build()
                )
        );

        return ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .name(chatRoom.getName())
                .messageList(chatMessageDTOList)
                .userList(chatRoomUserResponseDTOList)
                .build();

    }

    @Override
    public ChatRoomGetListWrapperResponseDTO getChatRoomList() {

        User me = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        // 내 채팅방 목록
        List<ChatRoom> chatRoomList = chatRoomUserRepository.findChatRoomByUserIdAndDelete(me.getId(), false).orElse(null);
        System.out.println("getChatRoomList : chatRoomList - " + chatRoomList);

        List<ChatRoomGetListResponseDTO> chatRooms = new ArrayList<>();

        chatRoomList.forEach(room -> {
            ChatMessageResponseDTO chatMessageResponseDTO = chatMessageService.getLastChatMessageValue(room.getId());

            chatRooms.add(
                    ChatRoomGetListResponseDTO.builder()
                            .roomId(room.getId())
                            .name(room.getName())
                            .message(chatMessageResponseDTO)
                            .userCount(chatRoomUserRepository.countByChatRoom(room)
                                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 없습니다.")))
                            .build()
            );
        });

        chatRooms.sort((o1, o2) -> {
            if (o1.getMessage() == null || o2.getMessage() == null) return 0;

            return o2.getMessage().getSendTime().compareTo(o1.getMessage().getSendTime());
        });

        return ChatRoomGetListWrapperResponseDTO.builder().chatRooms(chatRooms).build();

    }

    @Override
    public void addUser(String roomId, ChatRoomUserRequestDTO chatRoomUserRequestDTO) throws Exception {

        User user = userRepository.findById(chatRoomUserRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방 정보가 존재하지 않습니다."));

        if (chatRoomUserRepository.existsByUserAndChatRoom(user, chatRoom)) throw new Exception("회원 정보가 이미 존재합니다.");

        chatRoomUserRepository.save(
                ChatRoomUser.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .build()
        );

        chatRoom.increaseUserCount();

        chatRoomRepository.save(chatRoom);

    }

    @Override
    public void leaveChatRoom(String roomId) {

        User me = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방 정보가 존재하지 않습니다."));

        room.decreaseUserCount();

        chatRoomRepository.save(room);

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByUserAndChatRoom(me, room)
                .orElseThrow(() -> new ChatRoomUserNotFoundException("채팅방 내 회원 정보가 존재하지 않습니다."));

        chatRoomUser.changeDelete();

        chatRoomUserRepository.save(chatRoomUser);

    }

}
