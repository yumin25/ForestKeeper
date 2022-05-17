package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainVisiterRankResponseDTO")
@Getter
@ToString
@Builder
public class MountainVisiterRankResponseDTO {
    private String mountainName;
    private String mountainCode;
    private String address;
    private long visiterCount;
}
