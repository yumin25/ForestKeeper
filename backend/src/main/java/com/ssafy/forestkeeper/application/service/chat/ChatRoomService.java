package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomResponseDTO;

public interface ChatRoomService {

    void createChatRoom(ChatRoomRegisterRequestDTO chatRoomRegisterRequestDTO);

    ChatRoomResponseDTO enterChatRoom(String roomId);

    ChatRoomGetListWrapperResponseDTO getChatRoomList();

    void addUser(String roomId, ChatRoomUserRequestDTO chatRoomUserRequestDTO) throws Exception;

    void leaveChatRoom(String roomId);

}
