package com.ssafy.forestkeeper.application.dto.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("CommentRegisterPostDTO")
@Getter
@ToString
public class CommentRegisterRequestDTO {

    @ApiModelProperty(name = "글 ID")
    @NotBlank
    private String communityId;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String content;

}
