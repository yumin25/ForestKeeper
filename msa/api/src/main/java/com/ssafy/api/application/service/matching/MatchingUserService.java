package com.ssafy.api.application.service.matching;

public interface MatchingUserService {

    void joinMatching(String matchingId, String email);

    boolean isJoin(String matchingId, String email);

    int getParticipant(String matchingId);

    void cancelMatching(String matchingId, String email);
}
