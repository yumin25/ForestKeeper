package com.ssafy.forestkeeper.application.service.matching;

import com.ssafy.forestkeeper.application.dto.response.user.UserResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingRepository;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingUserRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.MatchingNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .matching(matchingRepository.findById(matchingId)
                        .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")))
                .build();

        matchingUserRepository.save(matchingUser);

    }

    @Override
    public boolean isJoin(String matchingId) {

        List<MatchingUser> matchingUserList = matchingUserRepository.findByMatchingAndDelete(
                matchingRepository.findById(matchingId)
                        .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")), false
        ).orElse(null);

        for (MatchingUser matchingUser : matchingUserList) {
            if (matchingUser.getUser() == userRepository.findByEmailAndDelete(
                            SecurityContextHolder.getContext().getAuthentication().getName(), false)
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."))
            ) {
                return true;
            }
        }

        return false;

    }

    @Override
    public List<UserResponseDTO> getParticipants(String matchingId) {

        List<MatchingUser> matchingUserList = matchingUserRepository.findByMatchingAndDelete(
                matchingRepository.findById(matchingId)
                        .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")), false
        ).orElse(null);

        return matchingUserList.stream().map(matchingUser -> UserResponseDTO.builder()
                        .userId(matchingUser.getUser().getId())
                        .nickname(matchingUser.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public void cancelMatching(String matchingId) {

        MatchingUser matchingUser = matchingUserRepository.findByMatchingAndUser(
                matchingRepository.findById(matchingId)
                        .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")),
                userRepository.findByEmailAndDelete(
                                SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."))
        ).orElseThrow(() -> new UserNotFoundException("매칭에 대한 회원 정보가 존재하지 않습니다."));

        matchingUser.changeDelete();

        matchingUserRepository.save(matchingUser);

    }

}
