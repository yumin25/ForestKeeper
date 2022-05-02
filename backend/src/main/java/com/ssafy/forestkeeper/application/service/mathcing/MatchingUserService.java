package com.ssafy.forestkeeper.application.service.mathcing;

public interface MatchingUserService {
    void joinMatching(String matchingId);
    boolean isJoin(String matchingId);
    int getParticipant(String matchingId);
}
