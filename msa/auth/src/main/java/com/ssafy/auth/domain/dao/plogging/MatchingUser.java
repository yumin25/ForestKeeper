package com.ssafy.auth.domain.dao.plogging;

import com.ssafy.auth.domain.dao.BaseEntity;
import com.ssafy.auth.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchingUser extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    @Column(name = "matching_is_deleted")
    private boolean delete;

    public void changeDelete() {
        this.delete = true;
    }
}
