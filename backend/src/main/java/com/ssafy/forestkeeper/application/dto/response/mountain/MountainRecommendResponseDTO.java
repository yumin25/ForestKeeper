package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainRecommendResponseDTO")
@Builder
@Getter
@ToString
public class MountainRecommendResponseDTO {

    private String mountainCode;

    private String name;

    private String address;

    private double value;

}
