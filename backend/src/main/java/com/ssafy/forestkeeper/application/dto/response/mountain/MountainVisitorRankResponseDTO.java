package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainVisitorRankResponseDTO")
@Builder
@Getter
@ToString
public class MountainVisitorRankResponseDTO {

    @ApiModelProperty(name = "산 이름")
    private String mountainName;

    @ApiModelProperty(name = "산 코드")
    private String mountainCode;

    @ApiModelProperty(name = "산 주소")
    private String address;

    @ApiModelProperty(name = "방문자 수")
    private long visitorCount;

}
