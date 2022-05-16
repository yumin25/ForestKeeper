package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("UserLoginResponseDTO")
@Builder
@Getter
@ToString
public class UserLoginResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "Access Token")
    private String accessToken;

    public static UserLoginResponseDTO of(String message, Integer statusCode, UserLoginResponseDTO userLoginResponseDTO) {

        userLoginResponseDTO.setMessage(message);
        userLoginResponseDTO.setStatusCode(statusCode);

        return userLoginResponseDTO;

    }
}
