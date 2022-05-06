package com.ssafy.auth.application.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@ApiModel("UserSignUpDTO")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSignUpDTO {

    @ApiModelProperty(name = "이메일")
    @NotBlank
    private String email;

    @ApiModelProperty(name = "비밀번호")
    @NotBlank
    private String password;

    @ApiModelProperty(name = "이름")
    @NotBlank
    private String name;

    @ApiModelProperty(name = "닉네임")
    @NotBlank
    private String nickname;

    @ApiModelProperty(name = "프로필 사진")
    @Nullable
    private MultipartFile image;
}
