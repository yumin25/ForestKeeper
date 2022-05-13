package com.ssafy.api.application.dto.response.mountain;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONObject;

@ApiModel("MountainTrailResponseDTO")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class MountainTrailResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "등산로")
    private JSONObject trail;

    public static MountainTrailResponseDTO of(String message, Integer statusCode,
        JSONObject trail) {

        MountainTrailResponseDTO mountainTrailResponseDTO = new MountainTrailResponseDTO();
        mountainTrailResponseDTO.setTrail(trail);
        mountainTrailResponseDTO.setMessage(message);
        mountainTrailResponseDTO.setStatusCode(statusCode);

        return mountainTrailResponseDTO;

    }
}
