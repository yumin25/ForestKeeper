package com.ssafy.forestkeeper.domain.dao.mountain;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.enums.RegionCode;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mountain extends BaseEntity {

    @Column(name = "mountain_code")
    private String code;

    @Column(name = "mountain_name")
    private String name;

    @Column(name = "mountain_height")
    private long height;

    @Column(name = "mountain_description")
    private String description;

    @Column(name = "mountain_address")
    private String address;

    @Column(name = "mountain_region_code")
    private RegionCode regionCode;

}
