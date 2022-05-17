package com.ssafy.forestkeeper.application.dto.response.user;

import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("UserResponseDTO")
@Builder
@Getter
@ToString
public class UserResponseDTO {

    @ApiModelProperty(name = "회원 ID")
    private String userId;

    @ApiModelProperty(name = "회원 닉네임")
    private String nickname;

}
