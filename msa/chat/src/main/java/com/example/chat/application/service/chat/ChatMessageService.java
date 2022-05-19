package com.example.chat.application.service.chat;

import com.example.chat.application.dto.chat.ChatMessageDTO;
import com.example.chat.application.dto.response.chat.ChatMessageResponseDTO;
import com.example.chat.domain.dao.chat.ChatRoom;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.List;

public interface ChatMessageService {

    void createChatRoom(ChatRoom chatRoom);

    void enterChatRoom(String roomId);

    ChannelTopic getTopic(String roomId);

    List<ChatMessageDTO> getChatMessageValue(String key);

    void setChatMessageValue(String key, ChatMessageDTO value);

    void setUserEnterInfo(String sessionId, String roomId);

    String getUserEnterRoomId(String sessionId);

    void removeUserEnterInfo(String sessionId);

    ChatMessageResponseDTO getLastChatMessageValue(String roomId);

}
