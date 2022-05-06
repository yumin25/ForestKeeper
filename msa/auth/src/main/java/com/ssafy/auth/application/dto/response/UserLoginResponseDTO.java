package com.ssafy.auth.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("UserLoginResponseDTO")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class UserLoginResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "Access Token")
    private String accessToken;

    public static UserLoginResponseDTO of(String accessToken, String message, Integer statusCode) {
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setAccessToken(accessToken);
        userLoginResponseDTO.setMessage(message);
        userLoginResponseDTO.setStatusCode(statusCode);
        return userLoginResponseDTO;
    }
}
