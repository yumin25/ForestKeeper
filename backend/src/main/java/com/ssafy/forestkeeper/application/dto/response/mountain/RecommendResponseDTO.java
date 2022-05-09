package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("RecommendResponseDTO")
@Getter
@ToString
@Builder

public class RecommendResponseDTO {

    private String mountainCode;
    private String name;
    private String address;
    private double value;
}
