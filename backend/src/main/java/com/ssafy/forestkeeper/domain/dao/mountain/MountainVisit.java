package com.ssafy.forestkeeper.domain.dao.mountain;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MountainVisit extends BaseEntity {

    @Column(name = "visiter_count")
    private long visiterCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_code")
    private Mountain mountain;

    public void increaseCount() {
        this.visiterCount += 1;
    }

}
