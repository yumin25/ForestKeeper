package com.ssafy.forestkeeper.application.dto.request.matching;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("MatchingRequestDTO")
@Getter
@ToString
public class MatchingRequestDTO {

    @ApiModelProperty(name = "매칭 ID")
    @NotBlank
    private String matchingId;

}
