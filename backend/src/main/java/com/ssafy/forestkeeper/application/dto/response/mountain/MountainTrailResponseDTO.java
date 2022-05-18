package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;

@ApiModel("MountainTrailResponseDTO")
@Getter
@ToString
@RequiredArgsConstructor
public class MountainTrailResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "등산로")
    private JSONObject trail;

    public static MountainTrailResponseDTO of(String message, Integer statusCode, JSONObject trail) {

        MountainTrailResponseDTO mountainTrailResponseDTO = new MountainTrailResponseDTO();

        mountainTrailResponseDTO.setMessage(message);
        mountainTrailResponseDTO.setStatusCode(statusCode);
        mountainTrailResponseDTO.trail = trail;

        return mountainTrailResponseDTO;

    }

}
