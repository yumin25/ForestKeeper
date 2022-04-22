package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
