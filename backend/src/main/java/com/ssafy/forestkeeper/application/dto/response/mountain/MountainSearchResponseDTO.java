package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainSearchResponseDTO")
@Builder
@Getter
@ToString
public class MountainSearchResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 검색 리스트")
    private List<MountainSearchDTO> mountainSearchDTOList;

    @ApiModelProperty(name = "검색된 산 개수")
    private int total;

    public static MountainSearchResponseDTO of(String message, Integer statusCode, MountainSearchResponseDTO mountainSearchResponseDTO) {

        mountainSearchResponseDTO.setMessage(message);
        mountainSearchResponseDTO.setStatusCode(statusCode);

        return mountainSearchResponseDTO;

    }

}
