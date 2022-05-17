package com.ssafy.forestkeeper.application.dto.response.mountain;

import java.util.List;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainVisiterRankWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainVisiterRankWrapperResponseDTO extends BaseResponseDTO{

    List<MountainVisiterRankResponseDTO> list;

    public static MountainVisiterRankWrapperResponseDTO of(String message, Integer statusCode, MountainVisiterRankWrapperResponseDTO mountainVisiterRankWrapperResponseDTO) {

    	mountainVisiterRankWrapperResponseDTO.setMessage(message);
    	mountainVisiterRankWrapperResponseDTO.setStatusCode(statusCode);

        return mountainVisiterRankWrapperResponseDTO;
    }
}
