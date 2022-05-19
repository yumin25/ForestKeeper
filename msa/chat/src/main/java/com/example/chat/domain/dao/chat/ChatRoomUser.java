package com.example.chat.domain.dao.chat;

import com.example.chat.domain.dao.BaseEntity;
import com.example.chat.domain.dao.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUser extends BaseEntity {

    @Column(name = "chat_room_user_is_deleted")
    private boolean delete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public void changeDelete() {
        this.delete = true;
    }

}

