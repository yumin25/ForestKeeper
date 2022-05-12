package com.ssafy.forestkeeper.application.dto.request.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("ChatRoomRegisterRequestDTO")
@Builder
@Getter
@ToString
public class ChatRoomRegisterRequestDTO {

    @ApiModelProperty(name = "채팅방 이름")
    private String name;

    @ApiModelProperty(name = "참여 인원")
    private List<String> userIdList;

}

