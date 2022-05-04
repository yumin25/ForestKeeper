package com.ssafy.forestkeeper.domain.repository.chat;

import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

}
