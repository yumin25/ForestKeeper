package com.ssafy.forestkeeper.application.dto.response.chat;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("ChatRoomWithMessageResponseDTO")
@Builder
@Getter
@ToString
public class ChatRoomWithMessageResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "채팅방 ID")
    private String roomId;

    @ApiModelProperty(name = "채팅방 이름")
    private String name;

    @ApiModelProperty(name = "참여 인원")
    private List<ChatRoomUserResponseDTO> userList;

    @ApiModelProperty(name = "메시지")
    private List<ChatMessageResponseDTO> messageList;

    public static ChatRoomWithMessageResponseDTO of(String message, Integer statusCode, ChatRoomWithMessageResponseDTO chatRoomResponseDTO) {

        chatRoomResponseDTO.setStatusCode(statusCode);
        chatRoomResponseDTO.setMessage(message);

        return chatRoomResponseDTO;

    }
}
