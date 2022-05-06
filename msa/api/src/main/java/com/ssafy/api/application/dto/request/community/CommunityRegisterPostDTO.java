package com.ssafy.api.application.dto.request.community;

import com.ssafy.api.domain.enums.CommunityCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("CommunityRegisterPostDTO")
@Getter
@ToString
public class CommunityRegisterPostDTO {

    @ApiModelProperty(name = "산 ID")
    @NotBlank
    private String mountainId;

    @ApiModelProperty(name = "커뮤니티 코드", example = "REVIEW or QNA")
    @NotNull
    private CommunityCode communityCode;

    @ApiModelProperty(name = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String description;

}