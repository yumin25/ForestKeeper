package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ApiModel("MountainSearchResponseDTO")
@Getter
@ToString
@RequiredArgsConstructor
public class MountainSearchResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 검색 리스트")
    private List<MountainSearchDTO> searchList;

    @ApiModelProperty(name = "전체 결과")
    private int total;

    public static MountainSearchResponseDTO of(String message, Integer statusCode, List<Mountain> ml, int total) {

        MountainSearchResponseDTO mountainSearchResponseDTO = new MountainSearchResponseDTO();

        mountainSearchResponseDTO.setMessage(message);
        mountainSearchResponseDTO.setStatusCode(statusCode);

        List<MountainSearchDTO> list = new ArrayList<>();

        for (Mountain mt : ml) {
            list.add(new MountainSearchDTO(mt.getCode(), mt.getName(), mt.getAddress()));
        }

        mountainSearchResponseDTO.searchList = list;
        mountainSearchResponseDTO.total = total;

        return mountainSearchResponseDTO;

    }

}
