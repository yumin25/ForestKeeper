package com.ssafy.forestkeeper.application.dto.response.plogging;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel("PloggingRegisterResponseDTO")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PloggingRegisterResponseDTO extends BaseResponseDTO{

    @ApiModelProperty(name = "플로깅 id")
    private String ploggingId;
    
    public static PloggingRegisterResponseDTO of(String message, Integer statusCode, PloggingRegisterResponseDTO ploggingRegisterResponseDTO) {

    	ploggingRegisterResponseDTO.setMessage(message);
    	ploggingRegisterResponseDTO.setStatusCode(statusCode);

        return ploggingRegisterResponseDTO;

    }
}
