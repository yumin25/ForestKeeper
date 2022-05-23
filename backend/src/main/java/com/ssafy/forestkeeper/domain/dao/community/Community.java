package com.ssafy.forestkeeper.domain.dao.community;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseEntity {

    @Column(name = "community_code")
    private CommunityCode communityCode;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "views")
    private long views;

    @Column(name = "delete_yn")
    private boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    // 글 수정
    public void changeCommunity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 조회수 증가
    public void increaseViews() {
        this.views += 1;
    }

    // 글 삭제
    public void changeDelete() {
        this.delete = true;
    }

}
