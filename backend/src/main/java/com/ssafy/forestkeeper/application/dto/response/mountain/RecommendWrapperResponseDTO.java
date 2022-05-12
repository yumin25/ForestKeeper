package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("RecommendWrapperResponseDTO")
@Builder
@Getter
@ToString
public class RecommendWrapperResponseDTO extends BaseResponseDTO {

    List<RecommendResponseDTO> recommendResponseDTOList;

    public static RecommendWrapperResponseDTO of(String message, Integer statusCode, RecommendWrapperResponseDTO recommendWrapperResponseDTO) {

        recommendWrapperResponseDTO.setMessage(message);
        recommendWrapperResponseDTO.setStatusCode(statusCode);

        return recommendWrapperResponseDTO;
    }
}
