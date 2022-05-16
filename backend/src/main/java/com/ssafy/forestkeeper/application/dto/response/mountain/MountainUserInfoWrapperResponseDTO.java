package com.ssafy.forestkeeper.application.dto.response.mountain;

import java.util.List;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel("MountainUserInfoWrapperResponseDTO")
@Builder
@Getter
@ToString
public class MountainUserInfoWrapperResponseDTO  extends BaseResponseDTO{

    List<MountainUserInfoResponseDTO> list;

    public static MountainUserInfoWrapperResponseDTO of(String message, Integer statusCode, MountainUserInfoWrapperResponseDTO mountainUserInfoWrapperResponseDTO) {

    	mountainUserInfoWrapperResponseDTO.setMessage(message);
    	mountainUserInfoWrapperResponseDTO.setStatusCode(statusCode);

        return mountainUserInfoWrapperResponseDTO;
    }
}
