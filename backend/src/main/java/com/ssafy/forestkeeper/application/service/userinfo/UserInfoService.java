package com.ssafy.forestkeeper.application.service.userinfo;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserPloggingInfoDTO;

public interface UserInfoService {

    PloggingGetListWrapperResponseDTO getPloggingList(int page);

    MountainUserInfoWrapperResponseDTO getMountainList(int page);

    PloggingGetListWrapperResponseDTO getPloggingInMountain(String mountainName);

    UserPloggingInfoDTO getUserAccumulative();

}
