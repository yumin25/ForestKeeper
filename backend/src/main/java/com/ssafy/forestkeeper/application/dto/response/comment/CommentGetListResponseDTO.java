package com.ssafy.forestkeeper.application.dto.response.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("CommentGetListResponseDTO")
@Builder
@Getter
@ToString
public class CommentGetListResponseDTO {

    @ApiModelProperty(name = "댓글 ID")
    private String commentId;

    @ApiModelProperty(name = "작성자 닉네임")
    private String nickname;

    @ApiModelProperty(name = "내용")
    private String content;

    @ApiModelProperty(name = "작성 시간")
    private LocalDateTime createdAt;

}
