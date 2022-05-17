package com.ssafy.forestkeeper.application.dto.response.matching;

import com.ssafy.forestkeeper.application.dto.response.user.UserResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("CommunityGetListResponseDTO")
@Builder
@Getter
@ToString
public class MatchingGetListResponseDTO {

    @ApiModelProperty(name = "매칭 글 ID")
    private String id;

    @ApiModelProperty(name = "작성자 닉네임")
    private String nickname;

    @ApiModelProperty(name = "제목")
    private String title;

    @ApiModelProperty(name = "작성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "플로깅 날짜")
    private LocalDate ploggingDate;

    @ApiModelProperty(name = "총원")
    private int total;

    @ApiModelProperty(name = "참가 인원")
    private List<UserResponseDTO> participants;

    @ApiModelProperty(name = "산 이름")
    private String mountainName;

}
