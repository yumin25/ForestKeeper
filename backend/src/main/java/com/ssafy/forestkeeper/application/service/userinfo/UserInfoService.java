package com.ssafy.forestkeeper.application.service.userinfo;

import java.util.List;
import java.util.Optional;

import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListWrapperResponseDTO;

public interface UserInfoService {

	PloggingListWrapperResponseDTO getPloggingList(int page);
    Optional<List<String>> getMountainList(int page);
    PloggingListWrapperResponseDTO getPloggingInMountain(String mountainName);
}
