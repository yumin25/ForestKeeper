package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatMessageRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatMessageResponseDTO;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;

import java.util.List;

public interface ChatMessageService {

    ChatMessageResponseDTO enterChatRoom(ChatMessageRequestDTO message);

    ChatMessageResponseDTO sendChatMessage(ChatMessageRequestDTO message, String accessToken);

    List<ChatMessageResponseDTO> getChatMessageList(ChatRoom chatRoom);

    ChatMessageResponseDTO getLastChatMessage(ChatRoom chatRoom);

}
