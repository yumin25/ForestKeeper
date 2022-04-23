package com.ssafy.forestkeeper.application.dto.response.plogging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ApiModel("TrashcanDTO")
@Builder
@Data
@ToString
public class TrashCanDTO {

    @ApiModelProperty(name = "쓰레기통 타입")
    private String type;

    @ApiModelProperty(name = "위도")
    private String latitude;

    @ApiModelProperty(name = "경도")
    private String longitude;
    
    @ApiModelProperty(name = "주소")
    private String address;
}
