package com.ssafy.forestkeeper.application.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("PasswordModifyRequestDTO")
@ToString
@Getter
public class PasswordModifyRequestDTO {

    @ApiModelProperty(name = "현재 비밀번호")
    @NotBlank
    private String currentPassword;

    @ApiModelProperty(name = "새 비밀번호")
    @NotBlank
    private String newPassword;

}
