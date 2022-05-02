package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.domain.dao.plogging.Matching;

public interface MatchingUserService {
    void joinMatching(String matchingId);

    boolean isJoin(String matchingId);
}
