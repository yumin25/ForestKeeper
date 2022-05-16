package com.ssafy.forestkeeper.application.service.matching;

import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingRepository;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingUserRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingUserServiceImpl implements MatchingUserService {

    private final MatchingUserRepository matchingUserRepository;
    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;

    @Override
    public void joinMatching(String matchingId) {

        MatchingUser matchingUser = MatchingUser.builder().user(userRepository.findByEmailAndDelete(
                                SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                .matching(matchingRepository.findById(matchingId)
                        .orElseThrow(() -> new IllegalArgumentException("잘못된 매칭 정보입니다")))
                .build();

        matchingUserRepository.save(matchingUser);
    }

    @Override
    public boolean isJoin(String matchingId) {
        List<MatchingUser> matchingUserList = matchingUserRepository.findByMatchingAndDelete(
                matchingRepository.findById(matchingId).get(), false).get();

        for (MatchingUser matchingUser : matchingUserList) {
            if (matchingUser.getUser() == userRepository.findByEmailAndDelete(
                    SecurityContextHolder.getContext().getAuthentication().getName(), false).get()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getParticipant(String matchingId) {
        return matchingUserRepository.findByMatchingAndDelete(
                matchingRepository.findById(matchingId).get(), false).get().size();
    }

    @Override
    public void cancelMatching(String matchingId) {
        MatchingUser matchingUser = matchingUserRepository.findByMatchingAndUserId(
                        matchingRepository.findById(matchingId).get()
                        ,
                        userRepository.findByEmailAndDelete(
                                        SecurityContextHolder.getContext().getAuthentication().getName(), false)
                                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")).getId())
                .get();

        matchingUser.changeDelete();
        matchingUserRepository.save(matchingUser);
    }

}
