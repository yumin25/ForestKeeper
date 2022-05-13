package com.ssafy.forestkeeper.application.service.matching;

public interface MatchingUserService {

    void joinMatching(String matchingId);

    boolean isJoin(String matchingId);

    int getParticipant(String matchingId);

    void cancelMatching(String matchingId);

}
