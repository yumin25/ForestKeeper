package com.ssafy.api.application.dto.response.plogging;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("PloggingExperienceResponseDTO")
@Getter
@ToString
@Builder
public class PloggingExperienceResponseDTO extends BaseResponseDTO {
    @ApiModelProperty(name = "경험치")
    private int exp;

    public static PloggingExperienceResponseDTO of(String message, Integer statusCode, PloggingExperienceResponseDTO ploggingExperienceResponseDTO){
        ploggingExperienceResponseDTO.setMessage(message);
        ploggingExperienceResponseDTO.setStatusCode(statusCode);
        return ploggingExperienceResponseDTO;
    }
}
