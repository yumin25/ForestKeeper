package com.ssafy.forestkeeper.domain.dao.plogging;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
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
public class Plogging extends BaseEntity {

    @Column(name = "plogging_distance")
    private double distance;

    @Column(name = "plogging_start_time")
    private LocalDateTime startTime;

    @Column(name = "plogging_end_time")
    private LocalDateTime endTime;

    @Column(name = "plogging_duration_time")
    private String durationTime;

    @Column(name = "plogging_exp")
    private long exp;

    @Column(name = "plogging_coordinates", columnDefinition = "TEXT")
    private String coords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    public void changeExp(long exp) {
        this.exp = exp;
    }

}
