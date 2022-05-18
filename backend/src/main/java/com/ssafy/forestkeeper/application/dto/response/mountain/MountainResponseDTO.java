package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainResponseDTO")
@Builder
@Getter
@ToString
public class MountainResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 정보")
    private Mountain mountainInfo;

    public static MountainResponseDTO of(String message, Integer statusCode, MountainResponseDTO mountainResponseDTO) {

        mountainResponseDTO.setMessage(message);
        mountainResponseDTO.setStatusCode(statusCode);

        return mountainResponseDTO;

    }

}
