package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MountainSearchDTO {

    @ApiModelProperty(name = "산 코드")
    String mountainCode;

    @ApiModelProperty(name = "산 이름")
    String name;

    @ApiModelProperty(name = "산 주소")
    String address;

}
