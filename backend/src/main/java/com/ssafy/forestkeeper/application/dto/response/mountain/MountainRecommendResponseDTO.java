package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainRecommendResponseDTO")
@Builder
@Getter
@ToString
public class MountainRecommendResponseDTO {

    @ApiModelProperty(name = "산 코드")
    private String mountainCode;

    @ApiModelProperty(name = "산 이름")
    private String name;

    @ApiModelProperty(name = "산 주소")
    private String address;

    @ApiModelProperty(name = "산까지의 거리 / 산 높이")
    private double value;

}
