package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainRankResponseDTO")
@Builder
@Getter
@ToString
public class MountainRankResponseDTO {

    @ApiModelProperty(name = "회원 닉네임")
    private String nickname;

    @ApiModelProperty(name = "방문 횟수")
    private long count;

    @ApiModelProperty(name = "경험치")
    private long exp;

    @ApiModelProperty(name = "프로필 사진 주소")
    private String imagePath;

}
