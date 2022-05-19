package com.example.chat.repository.chat;

import com.example.chat.domain.dao.chat.ChatMessage;
import com.example.chat.domain.dao.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    Optional<List<ChatMessage>> findByChatRoom(ChatRoom chatRoom);

    Optional<ChatMessage> findTopByChatRoomOrderBySendTimeDesc(ChatRoom chatRoom);

}
