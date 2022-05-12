package com.ssafy.forestkeeper.domain.dao.chat;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom extends BaseEntity implements Serializable {

    // 채팅방 이름
    @Column(name = "chat_room_name")
    private String name;

    // 채팅방 인원 수
    @Column(name = "chat_room_user_count")
    private int userCount;

    @Column(name = "chat_room_is_deleted")
    private boolean delete;

    public void increaseUserCount() {
        this.userCount += 1;
    }

    public void decreaseUserCount() {
        this.userCount -= 1;
    }

    public void changeDelete() {
        this.delete = true;
    }

}
