package com.ssafy.forestkeeper.application.dto.request.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ApiModel("ChatRoomUserRequestDTO")
@Getter
@ToString
public class ChatRoomUserRequestDTO {

    @ApiModelProperty(name = "추가할 유저")
    private String userId;

}
