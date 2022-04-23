package com.ssafy.forestkeeper.application.dto.response.plogging;

import java.util.List;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("TrashCanListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class TrashCanListWrapperResponseDTO extends BaseResponseDTO{

    @ApiModelProperty(name = "쓰레기통 목록")
    private List<TrashCanDTO> list;

    public static TrashCanListWrapperResponseDTO of(String message, Integer statusCode, TrashCanListWrapperResponseDTO trashCanListWrapperResponseDTO) {

    	trashCanListWrapperResponseDTO.setMessage(message);
    	trashCanListWrapperResponseDTO.setStatusCode(statusCode);

        return trashCanListWrapperResponseDTO;

    }
}
