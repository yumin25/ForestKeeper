package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ApiModel("MountainUserInfoWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainUserInfoWrapperResponseDTO extends BaseResponseDTO {

    List<MountainUserInfoResponseDTO> mountainUserInfoResponseDTOList;

    public static MountainUserInfoWrapperResponseDTO of(String message, Integer statusCode, MountainUserInfoWrapperResponseDTO mountainUserInfoWrapperResponseDTO) {

        mountainUserInfoWrapperResponseDTO.setMessage(message);
        mountainUserInfoWrapperResponseDTO.setStatusCode(statusCode);

        return mountainUserInfoWrapperResponseDTO;

    }

}
