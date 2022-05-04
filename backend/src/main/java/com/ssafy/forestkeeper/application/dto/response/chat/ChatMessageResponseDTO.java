package com.ssafy.forestkeeper.application.dto.response.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("ChatMessageResponseDTO")
@Builder
@Getter
@ToString
public class ChatMessageResponseDTO {

    @ApiModelProperty(name = "메시지 내용")
    private String content;

    @ApiModelProperty(name = "메시지 보낸 시간")
    private LocalDateTime sendTime;

    @ApiModelProperty(name = "메시지 보낸 사용자 ID")
    private String userId;

    @ApiModelProperty(name = "메시지 보낸 사용자 닉네임")
    private String nickname;

}
