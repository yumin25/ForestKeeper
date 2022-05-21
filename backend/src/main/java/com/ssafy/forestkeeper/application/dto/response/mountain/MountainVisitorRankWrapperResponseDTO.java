package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainVisiterRankWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainVisitorRankWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 방문객 수 순위")
    List<MountainVisitorRankResponseDTO> mountainVisitorRankResponseDTOList;

    public static MountainVisitorRankWrapperResponseDTO of(String message, Integer statusCode, MountainVisitorRankWrapperResponseDTO mountainVisitorRankWrapperResponseDTO) {

        mountainVisitorRankWrapperResponseDTO.setMessage(message);
        mountainVisitorRankWrapperResponseDTO.setStatusCode(statusCode);

        return mountainVisitorRankWrapperResponseDTO;

    }

}
