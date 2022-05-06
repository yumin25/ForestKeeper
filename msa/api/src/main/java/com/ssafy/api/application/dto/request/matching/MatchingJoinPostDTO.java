package com.ssafy.api.application.dto.request.matching;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("MatchingJoinPostDTO")
@Getter
@ToString
public class MatchingJoinPostDTO {

    @ApiModelProperty(name = "매칭 Id")
    @NotBlank
    private String matchingId;

}
