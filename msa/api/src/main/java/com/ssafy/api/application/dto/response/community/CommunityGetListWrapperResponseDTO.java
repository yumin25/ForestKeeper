package com.ssafy.api.application.dto.response.community;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("CommunityGetListWrapperResponseDTO")
@Builder
@Getter
@ToString
public class CommunityGetListWrapperResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "글 목록")
    List<CommunityGetListResponseDTO> communityGetListResponseDTOList;

    public static CommunityGetListWrapperResponseDTO of(String message, Integer statusCode, CommunityGetListWrapperResponseDTO communityGetListWrapperResponseDTO) {

        communityGetListWrapperResponseDTO.setMessage(message);
        communityGetListWrapperResponseDTO.setStatusCode(statusCode);

        return communityGetListWrapperResponseDTO;

    }

}
