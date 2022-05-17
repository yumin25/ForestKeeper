package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainRecommendWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainRecommendWrapperResponseDTO extends BaseResponseDTO {

    List<MountainRecommendResponseDTO> mountainRecommendResponseDTOList;

    public static MountainRecommendWrapperResponseDTO of(String message, Integer statusCode, MountainRecommendWrapperResponseDTO mountainRecommendWrapperResponseDTO) {

        mountainRecommendWrapperResponseDTO.setMessage(message);
        mountainRecommendWrapperResponseDTO.setStatusCode(statusCode);

        return mountainRecommendWrapperResponseDTO;

    }

}
