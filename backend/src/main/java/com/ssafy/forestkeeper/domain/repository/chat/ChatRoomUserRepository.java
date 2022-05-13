package com.ssafy.forestkeeper.domain.repository.chat;

import com.ssafy.forestkeeper.domain.dao.chat.ChatRoom;
import com.ssafy.forestkeeper.domain.dao.chat.ChatRoomUser;
import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, String> {

    @Query("SELECT uc.chatRoom FROM ChatRoomUser uc JOIN User u ON uc.user = u WHERE u.id = :userId AND uc.delete = :delete")
    Optional<List<ChatRoom>> findChatRoomByUserIdAndDelete(@Param("userId") String userId, @Param("delete") boolean delete);

    @Query("SELECT uc.user FROM ChatRoomUser uc JOIN ChatRoom r ON uc.chatRoom = r WHERE r.id = :roomId AND uc.delete = :delete")
    Optional<List<User>> findUserByRoomIdAndDelete(@Param("roomId") String roomId, @Param("delete") boolean delete);

    Optional<Integer> countByChatRoom(ChatRoom chatRoom);

    boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    Optional<ChatRoomUser> findByUserAndChatRoom(User user, ChatRoom chatRoom);

}
