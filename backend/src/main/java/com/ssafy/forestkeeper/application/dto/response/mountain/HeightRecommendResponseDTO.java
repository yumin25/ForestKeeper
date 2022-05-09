package com.ssafy.forestkeeper.application.dto.response.mountain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("HeightRecommendResponseDTO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HeightRecommendResponseDTO extends RecommendResponseDTO {

    private double height;
}
