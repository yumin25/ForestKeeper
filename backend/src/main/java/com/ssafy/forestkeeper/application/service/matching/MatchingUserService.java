package com.ssafy.forestkeeper.application.service.matching;

import com.ssafy.forestkeeper.application.dto.response.user.UserResponseDTO;
import com.ssafy.forestkeeper.domain.dao.user.User;

import java.util.List;

public interface MatchingUserService {

    void joinMatching(String matchingId);

    boolean isJoin(String matchingId);

    List<UserResponseDTO> getParticipants(String matchingId);

    void cancelMatching(String matchingId);

}
