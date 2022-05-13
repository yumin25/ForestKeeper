package com.ssafy.api.application.dto.response.mountain;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("CommunityGetListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainRankWrapperResponseDTO extends BaseResponseDTO {

    List<MountainRankResponseDTO> mountainRankResponseDTOList;

    public static MountainRankWrapperResponseDTO of(String message, Integer statusCode, MountainRankWrapperResponseDTO mountainRankWrapperResponseDTO) {

        mountainRankWrapperResponseDTO.setMessage(message);
        mountainRankWrapperResponseDTO.setStatusCode(statusCode);

        return mountainRankWrapperResponseDTO;
    }
}
