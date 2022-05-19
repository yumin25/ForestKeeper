package com.example.chat.application.dto.response.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("ChatRoomUserResponseDTO")
@Builder
@Getter
@ToString
public class ChatRoomUserResponseDTO {

    @ApiModelProperty(name = "회원 ID")
    private String userId;

    @ApiModelProperty(name = "회원 닉네임")
    private String nickname;

}

