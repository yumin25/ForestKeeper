package com.ssafy.api.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("BaseResponseDTO")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class BaseResponseDTO {

    @ApiModelProperty(name = "응답 메시지", example = "성공했습니다.")
    String message;

    @ApiModelProperty(name = "응답 코드", example = "200")
    Integer statusCode;

    public static BaseResponseDTO of(String message, Integer statusCode) {

        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

        baseResponseDTO.message = message;
        baseResponseDTO.statusCode = statusCode;

        return baseResponseDTO;

    }

}
