package com.ssafy.forestkeeper.application.service.userinfo;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserPloggingInfoDTO;

public interface UserInfoService {

	PloggingListWrapperResponseDTO getPloggingList(int page);
	MountainUserInfoWrapperResponseDTO getMountainList(int page);
    PloggingListWrapperResponseDTO getPloggingInMountain(String mountainName);
    UserPloggingInfoDTO getUserAccumulative();
}
