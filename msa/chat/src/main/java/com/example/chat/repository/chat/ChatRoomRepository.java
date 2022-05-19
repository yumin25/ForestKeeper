package com.example.chat.repository.chat;

import com.example.chat.domain.dao.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByIdAndDelete(String roomId, boolean delete);

}
