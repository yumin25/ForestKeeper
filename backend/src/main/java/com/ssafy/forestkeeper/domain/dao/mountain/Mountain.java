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

    @Column(name = "mountain_address")
    private String address;

    @Column(name = "mountain_admin")
    private String admin;

    @Column(name = "mountain_admin_tel")
    private String tel;

    @Column(name = "mountain_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "mountain_height")
    private double height;

    @Column(name = "is_famous")
    private short isFamous;

    @Column(name = "famous_description", columnDefinition = "TEXT")
    private boolean famousDescription;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lng;

}
