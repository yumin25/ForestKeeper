package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainVisitorRankResponseDTO")
@Builder
@Getter
@ToString
public class MountainVisitorRankResponseDTO {

    private String mountainName;

    private String mountainCode;

    private String address;

    private long visitorCount;

}
