package com.ssafy.forestkeeper.application.dto.response.comment;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("CommentGetListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class CommentGetListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "댓글 목록")
    private List<CommentGetListResponseDTO> commentGetListResponseDTOList;

    public static CommentGetListWrapperResponseDTO of(String message, Integer statusCode, CommentGetListWrapperResponseDTO commentGetListWrapperResponseDTO) {

        commentGetListWrapperResponseDTO.setMessage(message);
        commentGetListWrapperResponseDTO.setStatusCode(statusCode);

        return commentGetListWrapperResponseDTO;

    }

}
