package com.ssafy.forestkeeper.application.dto.request.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ApiModel("ChatMessageRequestDTO")
@Getter
@ToString
public class ChatMessageRequestDTO {

    @ApiModelProperty(name = "메시지 내용")
    private String content;

    @ApiModelProperty(name = "채팅방 ID")
    private String roomId;

}
