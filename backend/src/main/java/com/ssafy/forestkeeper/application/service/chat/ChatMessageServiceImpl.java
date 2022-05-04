package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatMessageRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatMessageResponseDTO;
import com.ssafy.forestkeeper.domain.dao.chat.ChatMessage;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.repository.chat.ChatMessageRepository;
import com.ssafy.forestkeeper.domain.repository.chat.ChatRoomRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.ChatRoomNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageResponseDTO enterChatRoom(ChatMessageRequestDTO message) {

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        return ChatMessageResponseDTO.builder()
                .content(user.getNickname() + " 님이 채팅방에 입장했습니다.")
                .sendTime(LocalDateTime.now())
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();

    }

    @Override
    public ChatMessageResponseDTO sendChatMessage(ChatMessageRequestDTO message) {

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        // 메시지 저장
        ChatMessage chatMessage = saveMessage(message, user.getId());

        return ChatMessageResponseDTO.builder()
                .content(chatMessage.getContent())
                .sendTime(chatMessage.getSendTime())
                .userId(chatMessage.getWriter().getId())
                .nickname(chatMessage.getWriter().getNickname())
                .build();

    }

    @Override
    public List<ChatMessageResponseDTO> getChatMessageList(ChatRoom chatRoom) {

        // 1. roomId 로 메시지 불러오기
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoom(chatRoom).orElse(null);
        System.out.println("findMessages : chatMessage - " + chatMessageList);

        if (chatMessageList == null) return null;

        // 2. Response 에 메시지 담기
        List<ChatMessageResponseDTO> messages = new ArrayList<>();

        chatMessageList.forEach(message -> messages.add(
                        ChatMessageResponseDTO.builder()
                                .content(message.getContent())
                                .sendTime(message.getSendTime())
                                .userId(message.getWriter().getId())
                                .nickname(message.getWriter().getNickname())
                                .build()
                )
        );

        return messages;

    }

    @Override
    public ChatMessageResponseDTO getLastChatMessage(ChatRoom chatRoom) {

        ChatMessage chatMessage = chatMessageRepository.findTopByChatRoomOrderBySendTimeDesc(chatRoom).orElse(null);

        if (chatMessage == null) return null;

        return ChatMessageResponseDTO.builder()
                .content(chatMessage.getContent())
                .sendTime(chatMessage.getSendTime())
                .userId(chatMessage.getWriter().getId())
                .nickname(chatMessage.getWriter().getNickname())
                .build();

    }

    public ChatMessage saveMessage(ChatMessageRequestDTO message, String userId) {

        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방 정보가 존재하지 않습니다."));

        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        return chatMessageRepository.save(
                ChatMessage.builder()
                        .content(message.getContent())
                        .sendTime(LocalDateTime.now())
                        .chatRoom(chatRoom)
                        .writer(writer)
                        .build()
        );

    }

}
