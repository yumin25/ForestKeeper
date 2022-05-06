package com.ssafy.api.application.service.userinfo;

import com.ssafy.api.application.dto.response.plogging.PloggingListWrapperResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {

	PloggingListWrapperResponseDTO getPloggingList(int page, String email);
    Optional<List<String>> getMountainList(int page, String email);
    PloggingListWrapperResponseDTO getPloggingInMountain(String mountainName, String email);
}
