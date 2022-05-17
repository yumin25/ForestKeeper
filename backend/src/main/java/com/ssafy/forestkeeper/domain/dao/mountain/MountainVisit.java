package com.ssafy.forestkeeper.domain.dao.mountain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MountainVisit extends BaseEntity{

    @Column(name = "visiter_count")
    private long visiterCount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_code")
    private Mountain mountain;
    
    public void increaseCount() {
    	this.visiterCount += 1;
    }

}
