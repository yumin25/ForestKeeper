package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("PloggingDetailResponseDTO")
@Builder
@Getter
@ToString
@AllArgsConstructor
public class PloggingDetailResponseDTO extends BaseResponseDTO{

    @ApiModelProperty(name = "플로깅한 날짜")
    private String date;

    @ApiModelProperty(name = "산 이름")
    private String mountainName;

    @ApiModelProperty(name = "이동 거리")
    private double distance;

    @ApiModelProperty(name = "소요 시간")
    private String time;

    @ApiModelProperty(name = "경험치")
    private long exp;
    
    public static PloggingDetailResponseDTO of(String message, Integer statusCode, PloggingDetailResponseDTO ploggingDetailResponseDTO) {

    	ploggingDetailResponseDTO.setMessage(message);
    	ploggingDetailResponseDTO.setStatusCode(statusCode);

        return ploggingDetailResponseDTO;

    }
}
