package com.ssafy.forestkeeper.domain.dao.plogging;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
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

    @Column(name = "plogging_exp")
    private long exp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

}
