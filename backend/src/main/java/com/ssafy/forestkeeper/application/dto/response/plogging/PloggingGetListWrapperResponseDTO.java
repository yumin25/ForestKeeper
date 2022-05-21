package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("PloggingListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class PloggingGetListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "플로깅 목록")
    private List<PloggingGetListResponseDTO> ploggingGetListResponseDTOList;

    public static PloggingGetListWrapperResponseDTO of(String message, Integer statusCode, PloggingGetListWrapperResponseDTO ploggingGetListWrapperResponseDTO) {

        ploggingGetListWrapperResponseDTO.setMessage(message);
        ploggingGetListWrapperResponseDTO.setStatusCode(statusCode);

        return ploggingGetListWrapperResponseDTO;

    }

}
