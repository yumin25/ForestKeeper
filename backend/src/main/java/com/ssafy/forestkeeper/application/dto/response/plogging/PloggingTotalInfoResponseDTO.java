package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("PloggingTotalInfoResponseDTO")
@Builder
@Getter
@ToString
public class PloggingTotalInfoResponseDTO extends BaseResponseDTO{

    @ApiModelProperty(name = "플로깅 참여자 수")
    private long numberOfUsers;

    @ApiModelProperty(name = "총 플로깅 거리")
    private long totalDistance;
    
    public static PloggingTotalInfoResponseDTO of(String message, Integer statusCode, PloggingTotalInfoResponseDTO ploggingTotalInfoResponseDTO) {

    	ploggingTotalInfoResponseDTO.setMessage(message);
    	ploggingTotalInfoResponseDTO.setStatusCode(statusCode);

        return ploggingTotalInfoResponseDTO;

    }
}
