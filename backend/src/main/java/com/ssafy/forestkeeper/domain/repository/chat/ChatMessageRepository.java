package com.ssafy.forestkeeper.domain.repository.chat;

import com.ssafy.forestkeeper.domain.dao.chat.ChatMessage;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    Optional<List<ChatMessage>> findByChatRoom(ChatRoom chatRoom);

    Optional<ChatMessage> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

}
