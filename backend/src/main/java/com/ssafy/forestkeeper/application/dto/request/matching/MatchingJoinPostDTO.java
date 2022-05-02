package com.ssafy.forestkeeper.application.dto.request.matching;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MatchingJoinPostDTO")
@Getter
@ToString
public class MatchingJoinPostDTO {

    @ApiModelProperty(name = "매칭 Id")
    @NotBlank
    private String matchingId;

}
