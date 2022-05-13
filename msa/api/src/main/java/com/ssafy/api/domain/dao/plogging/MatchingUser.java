package com.ssafy.api.domain.dao.plogging;

import com.ssafy.api.domain.dao.BaseEntity;
import com.ssafy.api.domain.dao.user.User;
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
