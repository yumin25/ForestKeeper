package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("PloggingExperienceResponseDTO")
@Getter
@ToString
@Builder
public class PloggingExperienceResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "경험치")
    private int exp;

    @ApiModelProperty(name = "쓰레기 분류")
    private List<String> type;

    public static PloggingExperienceResponseDTO of(String message, Integer statusCode, PloggingExperienceResponseDTO ploggingExperienceResponseDTO) {

        ploggingExperienceResponseDTO.setMessage(message);
        ploggingExperienceResponseDTO.setStatusCode(statusCode);

        return ploggingExperienceResponseDTO;

    }

}
