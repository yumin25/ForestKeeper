package com.ssafy.forestkeeper.application.dto.response.mountain;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("MountainSearchResponseDTO")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class MountainSearchResponseDTO extends BaseResponseDTO {

    @ApiModelProperty(name = "산 검색 리스트")
    private List<MountainSearch> searchlist;

    public static MountainSearchResponseDTO of(String message, Integer statusCode,
        List<Mountain> ml) {

        MountainSearchResponseDTO mountainSearchResponseDTO = new MountainSearchResponseDTO();
        mountainSearchResponseDTO.setMessage(message);
        mountainSearchResponseDTO.setStatusCode(statusCode);

        List<MountainSearch> list = new ArrayList<>();

        for (Mountain mt : ml) {
            list.add(new MountainSearch(mt.getCode(), mt.getName(), mt.getAddress()));
        }

        mountainSearchResponseDTO.setSearchlist(list);

        return mountainSearchResponseDTO;

    }
}
