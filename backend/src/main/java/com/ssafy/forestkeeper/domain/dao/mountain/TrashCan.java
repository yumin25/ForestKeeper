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
public class TrashCan extends BaseEntity {

    @Column(name = "trash_can_latitude")
    private String latitude;

    @Column(name = "trash_can_longitude")
    private String longitude;

    @Column(name = "trash_can_address")
    private String address;

    @Column(name = "trash_can_region_code")
    private RegionCode regionCode;

    @Column(name = "trash_can_type")
    private String type;

}
