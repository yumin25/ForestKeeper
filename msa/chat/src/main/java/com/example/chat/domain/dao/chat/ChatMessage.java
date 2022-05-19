package com.example.chat.domain.dao.chat;

import com.example.chat.domain.dao.BaseEntity;
import com.example.chat.domain.dao.user.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity implements Serializable {

    // 인덱스
    @Column(name = "chat_message_index")
    private long index;

    // 내용
    @Column(name = "chat_message_content")
    private String content;

    // 시간
    @Column(name = "chat_message_send_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendTime;

    // 삭제 여부
    @Column(name = "chat_message_is_deleted")
    private boolean delete;

    // 채팅방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeDelete() {
        this.delete = true;
    }
}
