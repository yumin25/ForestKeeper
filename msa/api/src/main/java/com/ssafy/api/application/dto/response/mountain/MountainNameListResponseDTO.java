package com.ssafy.api.application.dto.response.mountain;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainNameListResponseDTO")
@Getter
@ToString
@Builder
public class MountainNameListResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "방문한 산 목록")
    private List<String> list;

    public static MountainNameListResponseDTO of(String message, Integer statusCode, MountainNameListResponseDTO mountainNameListResponseDTO) {

    	mountainNameListResponseDTO.setMessage(message);
    	mountainNameListResponseDTO.setStatusCode(statusCode);
    	
        return mountainNameListResponseDTO;

    }
}
