package com.example.chat.application.service.chat;

import com.example.chat.application.dto.request.chat.ChatRoomRegisterRequestDTO;
import com.example.chat.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.example.chat.application.dto.response.chat.ChatRoomGetListWrapperResponseDTO;
import com.example.chat.application.dto.response.chat.ChatRoomResponseDTO;

public interface ChatRoomService {

    void createChatRoom(ChatRoomRegisterRequestDTO chatRoomRegisterRequestDTO);

    ChatRoomResponseDTO enterChatRoom(String roomId);

    ChatRoomGetListWrapperResponseDTO getChatRoomList();

    void addUser(String roomId, ChatRoomUserRequestDTO chatRoomUserRequestDTO) throws Exception;

    void leaveChatRoom(String roomId);

}
