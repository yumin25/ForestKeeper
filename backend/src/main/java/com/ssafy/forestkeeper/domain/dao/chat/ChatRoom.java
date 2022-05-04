package com.ssafy.forestkeeper.domain.dao.chat;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom extends BaseEntity {

    // 채팅방 이름
    @Column(name = "chat_room_name")
    private String name;

    // 채팅방 인원 수
    @Column(name = "chat_room_user_count")
    private int userCount;

    public void increaseUserCount() {
        this.userCount += 1;
    }

    public void decreaseUserCount() {
        this.userCount -= 1;
    }

}
