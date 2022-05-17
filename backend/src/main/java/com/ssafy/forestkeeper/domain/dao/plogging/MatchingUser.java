package com.ssafy.forestkeeper.domain.dao.plogging;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.user.User;
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

    public void changeDeleteTrue() {
        this.delete = true;
    }

    public void changeDeleteFalse() {
        this.delete = false;
    }

}
