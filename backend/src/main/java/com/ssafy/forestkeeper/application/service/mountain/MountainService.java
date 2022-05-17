package com.ssafy.forestkeeper.application.service.mountain;

import java.util.List;
import java.util.Optional;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainVisitorRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRecommendWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;

public interface MountainService {

    MountainResponseDTO getMountainInfo(String mountainCode);

    Optional<List<Mountain>> searchMountain(String keyword, int page);


    MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode);

    MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode);

    int totalSearch(String keyword);

    MountainRecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng);

    MountainRecommendWrapperResponseDTO getRecommendByHeight();

    MountainVisitorRankWrapperResponseDTO getVisiterRank();
}
