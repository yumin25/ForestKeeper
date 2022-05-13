package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainUserInfoResponseDTO")
@Getter
@ToString
@Builder
public class MountainUserInfoResponseDTO{

    @ApiModelProperty(name = "방문한 산 목록")
    private String mountainName;
    
    @ApiModelProperty(name = "방문한 산 코드")
    private String mountainCode;
}
