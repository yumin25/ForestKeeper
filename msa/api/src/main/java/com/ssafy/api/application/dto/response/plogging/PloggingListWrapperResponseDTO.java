package com.ssafy.api.application.dto.response.plogging;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
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
public class PloggingListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "플로깅 목록")
    private List<PloggingListResponseDTO> list;

    public static PloggingListWrapperResponseDTO of(String message, Integer statusCode, PloggingListWrapperResponseDTO ploggingListWrapperResponseDTO) {

    	ploggingListWrapperResponseDTO.setMessage(message);
    	ploggingListWrapperResponseDTO.setStatusCode(statusCode);

        return ploggingListWrapperResponseDTO;

    }
}
