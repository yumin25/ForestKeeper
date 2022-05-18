package com.ssafy.forestkeeper.application.dto.response.chat;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("ChatRoomGetListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class ChatRoomGetListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "채팅방 목록")
    private List<ChatRoomGetListResponseDTO> chatRoomGetListResponseDTOList;

    public static ChatRoomGetListWrapperResponseDTO of(String message, Integer statusCode, ChatRoomGetListWrapperResponseDTO chatRoomGetListWrapperResponseDTO) {

        chatRoomGetListWrapperResponseDTO.setStatusCode(statusCode);
        chatRoomGetListWrapperResponseDTO.setMessage(message);

        return chatRoomGetListWrapperResponseDTO;

    }

}
