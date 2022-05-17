package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainVisiterRankWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainVisitorRankWrapperResponseDTO extends BaseResponseDTO {

    List<MountainVisitorRankResponseDTO> list;

    public static MountainVisitorRankWrapperResponseDTO of(String message, Integer statusCode, MountainVisitorRankWrapperResponseDTO mountainVisitorRankWrapperResponseDTO) {

        mountainVisitorRankWrapperResponseDTO.setMessage(message);
        mountainVisitorRankWrapperResponseDTO.setStatusCode(statusCode);

        return mountainVisitorRankWrapperResponseDTO;

    }

}
