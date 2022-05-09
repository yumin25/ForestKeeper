package com.ssafy.auth.application.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("UserLoginDTO")
@ToString
@Getter
@NoArgsConstructor
public class UserLoginDTO {
    @ApiModelProperty(name = "이메일")
    @NotBlank
    private String email;

    @ApiModelProperty(name = "비밀번호")
    @NotBlank
    private String password;
}