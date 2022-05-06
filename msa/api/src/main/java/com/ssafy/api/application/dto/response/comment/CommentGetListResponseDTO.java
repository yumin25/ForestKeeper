package com.ssafy.api.application.dto.response.comment;

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

    @ApiModelProperty(name = "작성자 닉네임")
    private String nickname;

    @ApiModelProperty(name = "내용")
    private String description;

    @ApiModelProperty(name = "작성 시간")
    private LocalDateTime createTime;

}
