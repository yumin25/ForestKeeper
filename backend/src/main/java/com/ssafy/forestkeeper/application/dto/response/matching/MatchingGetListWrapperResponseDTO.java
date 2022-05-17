package com.ssafy.forestkeeper.application.dto.response.matching;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MatchingGetListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MatchingGetListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "글 목록")
    List<MatchingGetListResponseDTO> matchingGetListResponseDTOList;

    public static MatchingGetListWrapperResponseDTO of(String message, Integer statusCode, MatchingGetListWrapperResponseDTO matchingGetListWrapperResponseDTO) {

        matchingGetListWrapperResponseDTO.setMessage(message);
        matchingGetListWrapperResponseDTO.setStatusCode(statusCode);

        return matchingGetListWrapperResponseDTO;

    }

}
