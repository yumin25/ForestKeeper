package com.ssafy.forestkeeper.domain.dao.community;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Column(name = "comment_description")
    @Lob
    private String description;

    @Column(name = "comment_create_time")
    private LocalDateTime createTime;

    @Column(name = "comment_is_deleted")
    private boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    // 댓글 삭제
    public void changeDelete() {
        this.delete = true;
    }

}
