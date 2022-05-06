package com.ssafy.api.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainRankResponseDTO")
@Builder
@Getter
@ToString
public class MountainRankResponseDTO {

    private String nickname;
    private long count;
    private double distance;
}
