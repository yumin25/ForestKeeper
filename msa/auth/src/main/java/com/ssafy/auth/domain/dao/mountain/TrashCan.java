package com.ssafy.auth.domain.dao.mountain;

import com.ssafy.auth.domain.dao.BaseEntity;
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

    @Column(name = "trash_can_region")
    private String region;

    @Column(name = "address_detail")
    private String detail;

    @Column(name = "trash_can_type")
    private short type;
}
