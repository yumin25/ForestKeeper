package com.ssafy.forestkeeper.domain.dao.mountain;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mountain extends BaseEntity implements Serializable {

    @Column(name = "mountain_code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "height")
    private double height;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lng;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_famous")
    private boolean famous;

    @Column(name = "famous_description", columnDefinition = "TEXT")
    private String famousDescription;

    @Column(name = "admin")
    private String admin;

    @Column(name = "admin_tel")
    private String adminTel;

}
