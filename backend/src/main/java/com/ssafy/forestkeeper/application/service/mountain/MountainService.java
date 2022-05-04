package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import java.util.List;
import java.util.Optional;

public interface MountainService {

    Optional<Mountain> getMountainInfo(String mountainCode);
    Optional<List<Mountain>> searchMountain(String keyword, int page);


    MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode);
    MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode);

    int totalSearch(String keyword);

}
