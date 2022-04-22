package com.ssafy.forestkeeper.application.dto.response.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("CommunityGetListResponseDTO")
@Builder
@Getter
@ToString
public class CommunityGetListResponseDTO {

    @ApiModelProperty(name = "작성자 닉네임")
    private String nickname;

    @ApiModelProperty(name = "제목")
    private String title;

    @ApiModelProperty(name = "작성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "댓글 수")
    private long comments;

}
