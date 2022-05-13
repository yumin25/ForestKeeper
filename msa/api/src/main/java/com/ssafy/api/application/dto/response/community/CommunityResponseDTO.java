package com.ssafy.api.application.dto.response.community;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("CommunityResponseDTO")
@Builder
@Getter
@ToString
public class CommunityResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "작성자 닉네임")
    private String nickname;

    @ApiModelProperty(name = "제목")
    private String title;

    @ApiModelProperty(name = "내용")
    private String description;

    @ApiModelProperty(name = "작성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "조회수")
    private long views;

//    @ApiModelProperty(name = "댓글 목록")
//    private List<CommentResponseDTO> commentResponseDTOList;

    public static CommunityResponseDTO of(String message, Integer statusCode, CommunityResponseDTO communityResponseDTO) {

        communityResponseDTO.setMessage(message);
        communityResponseDTO.setStatusCode(statusCode);

        return communityResponseDTO;

    }

}
