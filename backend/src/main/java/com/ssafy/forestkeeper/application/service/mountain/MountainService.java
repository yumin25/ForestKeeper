package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.application.dto.response.mountain.*;

public interface MountainService {

    MountainResponseDTO getMountainInfo(String mountainCode);

    MountainSearchResponseDTO searchMountain(String keyword, int page);

    MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode);

    MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode);

    MountainRecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng);

    MountainRecommendWrapperResponseDTO getRecommendByHeight();

    MountainVisitorRankWrapperResponseDTO getVisitorRank();

}
