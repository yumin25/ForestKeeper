package com.ssafy.forestkeeper.application.dto.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("CommunityModifyPatchDTO")
@Getter
@ToString
public class CommunityModifyPatchDTO {

    @ApiModelProperty(name = "글 ID")
    @NotBlank
    private String communityId;

    @ApiModelProperty(name = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String description;

}
