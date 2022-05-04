package com.ssafy.forestkeeper.application.service.chat;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomWithMessageResponseDTO;

public interface ChatRoomService {

    void createChatRoom(ChatRoomRegisterPostDTO chatRoomRegisterPostDTO);

    ChatRoomWithMessageResponseDTO getChatRoom(String roomId);

    ChatRoomGetListWrapperResponseDTO getChatRoomList();

    void addUser(String roomId, ChatRoomUserRequestDTO chatRoomUserRequestDTO) throws Exception;

    void leaveChatRoom(String roomId);

}
