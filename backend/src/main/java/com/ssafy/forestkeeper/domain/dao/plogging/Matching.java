package com.ssafy.forestkeeper.domain.dao.plogging;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Matching extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "plogging_date")
    private LocalDate ploggingDate;

    @Column(name = "total")
    private int total;

    @Column(name = "views")
    private long views;

    @Column(name = "is_closed")
    private boolean close;

    @Column(name = "is_deleted")
    private boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_code", referencedColumnName = "mountain_code")
    private Mountain mountain;

    @OneToMany(mappedBy = "matching", fetch = FetchType.LAZY)
    private List<MatchingUser> matchingUsers = new ArrayList<>();

    // 조회수 증가
    public void increaseViews() {
        this.views += 1;
    }

    public void changeClose() {
        this.close = true;
    }

    public void changeDelete() {
        this.delete = true;
    }

    public void changeMatching(String title, String content, LocalDate ploggingDate, int total, Mountain mountain) {
        this.title = title;
        this.content = content;
        this.ploggingDate = ploggingDate;
        this.total = total;
        this.mountain = mountain;
    }

}
