package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.*;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.dao.chat.UserChatRoomJoin;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomRepository;
import com.ssafy.forestkeeper.domain.repository.chat.UserChatRoomJoinRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.ChatRoomNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    private final UserChatRoomJoinRepository userChatRoomJoinRepository;

    private final ChatMessageService chatMessageService;

    @Override
    public void createChatRoom(ChatRoomRegisterPostDTO chatRoomRegisterPostDTO) {
        System.out.println(0);
        // 내 정보
        User me = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));
        System.out.println(1);
        // 채팅방 이름
        String name = chatRoomRegisterPostDTO.getName();
        System.out.println(2);
        // 채팅방 참여 인원 ID 목록
        List<String> userList = chatRoomRegisterPostDTO.getUserIdList();
        System.out.println(3);
        // 내 ID 추가
        userList.add(me.getId());
        System.out.println(4);
        // 채팅방 생성
        ChatRoom room = chatRoomRepository.save(
                ChatRoom.builder()
                        .name(name)
                        .userCount(userList.size())
                        .build()
        );
        System.out.println(5);
        for (String userId : userList) {
            // 유저 정보
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

            // User-ChatRoom 조인 테이블 저장
            userChatRoomJoinRepository.save(
                    UserChatRoomJoin.builder()
                            .user(user)
                            .chatRoom(room)
                            .build()
            );
        }

    }

    @Override
    public ChatRoomWithMessageResponseDTO getChatRoom(String roomId) {

        // 채팅방 전체 메시지
        List<ChatMessageResponseDTO> messages = chatMessageService
                .getChatMessageList(chatRoomRepository.findById(roomId).orElseThrow());

        // 채팅방 참여 인원
        List<User> userList = userChatRoomJoinRepository.findByRoomId(roomId)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));
        System.out.println("userList: " + userList);

        List<ChatRoomUserResponseDTO> users = new ArrayList<>();

        userList.forEach(user -> users.add(
                        ChatRoomUserResponseDTO.builder()
                                .userId(user.getId())
                                .nickname(user.getNickname())
                                .build()
                )
        );

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방 정보가 존재하지 않습니다."));

        return ChatRoomWithMessageResponseDTO.builder()
                .roomId(room.getId())
                .name(room.getName())
                .messageList(messages)
                .userList(users)
                .build();

    }

    @Override
    public ChatRoomGetListWrapperResponseDTO getChatRoomList() {

        User me = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        // 내 채팅방 목록
        List<ChatRoom> chatRoomList = userChatRoomJoinRepository.findByUserId(me.getId()).orElse(null);
        System.out.println("findAllRooms : chatRoomList - " + chatRoomList);

        List<ChatRoomGetListResponseDTO> chatRooms = new ArrayList<>();

        chatRoomList.forEach(room -> {
            ChatMessageResponseDTO chatMessageResponseDTO = chatMessageService.getLastChatMessage(room);

            chatRooms.add(
                    ChatRoomGetListResponseDTO.builder()
                            .roomId(room.getId())
                            .name(room.getName())
                            .message(chatMessageResponseDTO)
                            .userCount(userChatRoomJoinRepository.countByChatRoom(room)
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

        if (userChatRoomJoinRepository.existsByUserAndChatRoom(user, chatRoom)) throw new Exception("회원 정보가 이미 존재합니다.");

        userChatRoomJoinRepository.save(
                UserChatRoomJoin.builder()
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

        UserChatRoomJoin userChatRoomJoin = userChatRoomJoinRepository.findByUserAndChatRoom(me, room)
                .orElseThrow();

        userChatRoomJoin.changeDelete();

        userChatRoomJoinRepository.save(userChatRoomJoin);

    }
}
