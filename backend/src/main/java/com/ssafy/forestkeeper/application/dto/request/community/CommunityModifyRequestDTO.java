package com.ssafy.forestkeeper.application.dto.request.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("CommunityModifyRequestDTO")
@Getter
@ToString
public class CommunityModifyRequestDTO {

    @ApiModelProperty(name = "글 ID")
    @NotBlank
    private String communityId;

    @ApiModelProperty(name = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String content;

}
