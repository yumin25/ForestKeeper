package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("PloggingCumulativeResponseDTO")
@Builder
@Getter
@ToString
@AllArgsConstructor
public class PloggingCumulativeResponseDTO extends BaseResponseDTO{

    @ApiModelProperty(name = "누적 방문자 수")
    private int visiter;
    
    @ApiModelProperty(name = "누적 거리")
    private long distance;
    
    public static PloggingCumulativeResponseDTO of(String message, Integer statusCode, PloggingCumulativeResponseDTO ploggingCumulativeResponseDTO) {

    	ploggingCumulativeResponseDTO.setMessage(message);
    	ploggingCumulativeResponseDTO.setStatusCode(statusCode);

        return ploggingCumulativeResponseDTO;

    }
}
