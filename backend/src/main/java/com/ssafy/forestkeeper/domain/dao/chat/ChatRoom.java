package com.ssafy.forestkeeper.domain.dao.chat;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity implements Serializable {

    // 채팅방 이름
    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private boolean delete;

    public void changeDelete() {
        this.delete = true;
    }

}
