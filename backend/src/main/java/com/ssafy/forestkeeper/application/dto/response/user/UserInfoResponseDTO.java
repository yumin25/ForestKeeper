package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("UserInfoDTO")
@Builder
@Getter
@ToString
public class UserInfoResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "닉네임")
    private String nickname;

    @ApiModelProperty(name = "이메일")
    private String email;

    @ApiModelProperty(name = "이름")
    private String name;

    @ApiModelProperty(name = "프로필 사진 주소")
    private String imagePath;

    @ApiModelProperty(name = "프로필 썸네일 주소")
    private String thumbnailPath;

    public static UserInfoResponseDTO of(String message, Integer statusCode, UserInfoResponseDTO userInfoResponseDTO) {

        userInfoResponseDTO.setMessage(message);
        userInfoResponseDTO.setStatusCode(statusCode);

        return userInfoResponseDTO;

    }

}
