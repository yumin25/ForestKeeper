package com.ssafy.forestkeeper.application.dto.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("UserRegisterPostDTO")
@Getter
@ToString
@NoArgsConstructor
public class UserRegisterPostDTO {

    @ApiModelProperty(name = "이름")
    @NotBlank
    private String name;

}
