package com.ssafy.forestkeeper.application.dto.request.plogging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel("PloggingRegisterRequestDTO")
@Getter
@ToString
public class PloggingRegisterRequestDTO {

    @ApiModelProperty(name = "플로깅 시작 시간")
    @NotBlank
    private String startTime;

    @ApiModelProperty(name = "플로깅 종료 시간")
    @NotBlank
    private String endTime;

    @ApiModelProperty(name = "플로깅 거리")
    @NotBlank
    private double distance;

    @ApiModelProperty(name = "산 코드")
    @NotBlank
    private String mountainCode;

    @ApiModelProperty(name = "이동 좌표")
    @NotBlank
    private List<CoordinatesDTO> coords;

}
