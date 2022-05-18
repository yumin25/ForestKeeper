package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("UserPloggingInfoDTO")
@Builder
@Getter
@ToString
public class UserPloggingInfoDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "누적 거리")
    private double distance;

    @ApiModelProperty(name = "누적 시간")
    private String time;

    @ApiModelProperty(name = "누적 경험치")
    private int exp;

    public static UserPloggingInfoDTO of(String message, Integer statusCode, UserPloggingInfoDTO userPloggingInfoDTO) {

        userPloggingInfoDTO.setMessage(message);
        userPloggingInfoDTO.setStatusCode(statusCode);

        return userPloggingInfoDTO;

    }

}
