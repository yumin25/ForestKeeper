package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingRepository;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingUserRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
