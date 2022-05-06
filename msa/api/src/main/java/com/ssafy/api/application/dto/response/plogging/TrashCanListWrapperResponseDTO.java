package com.ssafy.api.application.dto.response.plogging;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import com.ssafy.api.domain.dao.mountain.TrashCan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("TrashCanListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class TrashCanListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "쓰레기통 목록")
    private List<TrashCan> list;

    public static TrashCanListWrapperResponseDTO of(String message, Integer statusCode, TrashCanListWrapperResponseDTO trashCanListWrapperResponseDTO) {

    	trashCanListWrapperResponseDTO.setMessage(message);
    	trashCanListWrapperResponseDTO.setStatusCode(statusCode);

        return trashCanListWrapperResponseDTO;
    }
}
