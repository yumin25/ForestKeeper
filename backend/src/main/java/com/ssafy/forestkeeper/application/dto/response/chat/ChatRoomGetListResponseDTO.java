package com.ssafy.forestkeeper.application.dto.response.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("ChatRoomResponseDTO")
@Builder
@Getter
@ToString
public class ChatRoomGetListResponseDTO {

    @ApiModelProperty(name = "채팅방 ID")
    private String roomId;

    @ApiModelProperty(name = "채팅방 이름")
    private String name;

    @ApiModelProperty(name = "참여 인원수")
    private int userCount;

    @ApiModelProperty(name = "최근 메시지")
    private ChatMessageResponseDTO message;

}
