package com.ssafy.forestkeeper.application.dto.request.matching;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("MatchingRegisterPostDTO")
@Getter
@ToString
public class MatchingRegisterPostDTO {

    @ApiModelProperty(name = "산 코드")
    @NotBlank
    private String mountainCode;

    @ApiModelProperty(name = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(name = "내용")
    @NotBlank
    private String content;

    @ApiModelProperty(name = "플로깅 날짜")
    @NotBlank
    private String ploggingDate;

    @ApiModelProperty(name = "총 인원")
    private int total;

}
