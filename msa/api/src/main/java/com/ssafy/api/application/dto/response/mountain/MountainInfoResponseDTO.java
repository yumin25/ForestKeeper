package com.ssafy.api.application.dto.response.mountain;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import com.ssafy.api.domain.dao.mountain.Mountain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ApiModel("MountainInfoResponseDTO")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MountainInfoResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 정보")
    private Mountain mountainInfo;

    public static MountainInfoResponseDTO of(String message, Integer statusCode,
        Mountain mountainInfo) {

        MountainInfoResponseDTO mountainInfoResponseDTO = new MountainInfoResponseDTO();
        mountainInfoResponseDTO.setMountainInfo(mountainInfo);
        mountainInfoResponseDTO.setMessage(message);
        mountainInfoResponseDTO.setStatusCode(statusCode);

        return mountainInfoResponseDTO;

    }
}
