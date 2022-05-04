package com.ssafy.forestkeeper.domain.dao.chat;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage extends BaseEntity {

    // 내용
    @Column(name = "chat_message_content")
    private String content;

    // 시간
    @Column(name = "chat_message_send_time")
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
    private User writer;

}
