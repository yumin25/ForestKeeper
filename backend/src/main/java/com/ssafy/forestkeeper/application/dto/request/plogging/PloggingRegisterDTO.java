package com.ssafy.forestkeeper.application.dto.request.plogging;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("PloggingRegisterDTO")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PloggingRegisterDTO {

    @ApiModelProperty(name = "플로깅 시작 시간")
    @NotBlank
    private LocalDateTime startTime;

    @ApiModelProperty(name = "플로깅 종료 시간")
    @NotBlank
    private LocalDateTime endTime;

    @ApiModelProperty(name = "플로깅 거리")
    @NotBlank
    private double distance;

    @ApiModelProperty(name = "유저 id")
    @NotBlank
    private String userId;
    
    @ApiModelProperty(name = "산 id")
    @NotBlank
    private String mountainId;
}
