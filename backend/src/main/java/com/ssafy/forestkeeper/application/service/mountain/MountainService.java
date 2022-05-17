package com.ssafy.forestkeeper.application.service.mountain;

import java.util.List;
import java.util.Optional;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainVisiterRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.RecommendWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;

public interface MountainService {

    Optional<Mountain> getMountainInfo(String mountainCode);

    Optional<List<Mountain>> searchMountain(String keyword, int page);


    MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode);

    MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode);

    int totalSearch(String keyword);

    RecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng);

    RecommendWrapperResponseDTO getRecommendByHeight();

    MountainVisiterRankWrapperResponseDTO getVisiterRank();
}
