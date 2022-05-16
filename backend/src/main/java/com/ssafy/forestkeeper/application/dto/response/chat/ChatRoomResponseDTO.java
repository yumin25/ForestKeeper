package com.ssafy.forestkeeper.application.dto.response.chat;

import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
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
public class ChatRoomResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "채팅방 ID")
    private String roomId;

    @ApiModelProperty(name = "채팅방 이름")
    private String name;

    @ApiModelProperty(name = "참여 인원")
    private List<ChatRoomUserResponseDTO> userList;

    @ApiModelProperty(name = "메시지")
    private List<ChatMessageDTO> messageList;

    public static ChatRoomResponseDTO of(String message, Integer statusCode, ChatRoomResponseDTO chatRoomResponseDTO) {

        chatRoomResponseDTO.setStatusCode(statusCode);
        chatRoomResponseDTO.setMessage(message);

        return chatRoomResponseDTO;

    }

}
