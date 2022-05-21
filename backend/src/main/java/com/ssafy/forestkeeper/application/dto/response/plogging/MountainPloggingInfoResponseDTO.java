package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainPloggingInfoResponseDTO")
@Builder
@Getter
@ToString
public class MountainPloggingInfoResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "누적 플로깅한 사람 수")
    private int visitor;

    @ApiModelProperty(name = "누적 거리")
    private long distance;

    @ApiModelProperty(name = "누적 방문자 수")
    private int count;

    public static MountainPloggingInfoResponseDTO of(String message, Integer statusCode, MountainPloggingInfoResponseDTO mountainPloggingInfoResponseDTO) {

        mountainPloggingInfoResponseDTO.setMessage(message);
        mountainPloggingInfoResponseDTO.setStatusCode(statusCode);

        return mountainPloggingInfoResponseDTO;

    }

}
