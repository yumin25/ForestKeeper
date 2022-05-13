package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("UserInfoDTO")
@Getter
@ToString
@Builder
public class UserInfoDTO extends BaseResponseDTO {

    @ApiModelProperty(name="닉네임")
    private String nickname;

    @ApiModelProperty(name="이메일")
    private String email;

    @ApiModelProperty(name="이름")
    private String name;
    
    @ApiModelProperty(name="프로필 사진 경로")
    private String imagePath;
    
    @ApiModelProperty(name="썸네일 사진 경로")
    private String thumbnailPath;

    public static UserInfoDTO of(String message, Integer statusCode, UserInfoDTO userInfoDTO){
        userInfoDTO.setMessage(message);
        userInfoDTO.setStatusCode(statusCode);
        return userInfoDTO;
    }
}
