package com.ssafy.forestkeeper.application.dto.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("CommentModifyPatchDTO")
@Getter
@ToString
public class CommentModifyRequestDTO {

    @ApiModelProperty(name = "댓글 ID")
    @NotBlank
    private String commentId;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String description;

}
