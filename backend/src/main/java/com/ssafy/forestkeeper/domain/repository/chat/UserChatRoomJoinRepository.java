package com.ssafy.forestkeeper.domain.repository.chat;

import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.dao.chat.UserChatRoomJoin;
import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomJoinRepository extends JpaRepository<UserChatRoomJoin, String> {

    @Query("SELECT uc.chatRoom FROM UserChatRoomJoin uc JOIN User u ON (uc.user = u AND u.id = :userId)")
    Optional<List<ChatRoom>> findByUserId(@Param("userId") String userId);

    @Query("SELECT uc.user FROM UserChatRoomJoin uc JOIN ChatRoom r ON (uc.chatRoom = r AND r.id = :roomId)")
    Optional<List<User>> findByRoomId(@Param("roomId") String roomId);

    Optional<Integer> countByChatRoom(ChatRoom chatRoom);

    boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    Optional<UserChatRoomJoin> findByUserAndChatRoom(User user, ChatRoom chatRoom);

}
