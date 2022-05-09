package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("RecommendResponseDTO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecommendResponseDTO {

    private String mountainCode;
    private String name;
    private String address;
}
