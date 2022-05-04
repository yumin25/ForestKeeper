package com.ssafy.forestkeeper.application.dto.response.plogging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("PloggingListResponseDTO")
@Builder
@Getter
@ToString
public class PloggingListResponseDTO {
    @ApiModelProperty(name = "플로깅 id")
    private String ploggingId;

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
    
    @ApiModelProperty(name = "이동 경로 이미지")
    private String imagePath;
}
