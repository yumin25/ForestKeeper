package com.ssafy.forestkeeper.application.service.matching;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingRepository;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingUserRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.MatchingNotFoundException;
import com.ssafy.forestkeeper.exception.MountainNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MountainRepository mountainRepository;

    private final UserRepository userRepository;

    private final MatchingRepository matchingRepository;

    private final MatchingUserRepository matchingUserRepository;

    private final MatchingUserService matchingUserService;

    @Override
    public void registerMatching(MatchingRegisterRequestDTO matchingRegisterRequestDTO) {

        Matching matching = Matching.builder()
                .title(matchingRegisterRequestDTO.getTitle())
                .content(matchingRegisterRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .ploggingDate(LocalDate.parse(matchingRegisterRequestDTO.getPloggingDate()))
                .total(matchingRegisterRequestDTO.getTotal())
                .user(userRepository.findByEmailAndDelete(
                                SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .mountain(mountainRepository.findByCode(matchingRegisterRequestDTO.getMountainCode())
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다.")))
                .build();

        matchingRepository.save(matching);

        matchingUserRepository.save(MatchingUser.builder().user(userRepository.findByEmailAndDelete(
                                SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .matching(matching).build());

    }

    @Override
    public void modifyMatching(MatchingModifyRequestDTO matchingModifyRequestDTO) {

        Matching matching = matchingRepository.findByIdAndDelete(
                        matchingModifyRequestDTO.getMatchingId(), false)
                .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다."));

        matching.changeMatching(matchingModifyRequestDTO.getTitle(),
                matchingModifyRequestDTO.getContent(), LocalDate.parse(matchingModifyRequestDTO.getPloggingDate()),
                matchingModifyRequestDTO.getTotal(),
                mountainRepository.findByCode(matchingModifyRequestDTO.getMountainCode())
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다."))
        );

        matchingRepository.save(matching);

    }

    @Override
    public void closeMatching(String matchingId) {

        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다."));

        matching.changeClose();

        matchingRepository.save(matching);

    }

    @Override
    public boolean isFull(String matchingId) {

        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다."));

        List<MatchingUser> list = matching.getMatchingUsers();

        int total = 0;

        for (MatchingUser matchingUser : list) {
            if (!matchingUser.isDelete()) {
                total++;
            }
        }

        return total == matching.getTotal();

    }

    @Override
    public boolean isClose(String matchingId) {

        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")).isClose();

    }

    @Override
    public boolean isDelete(String matchingId) {

        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> new MatchingNotFoundException("매칭 정보가 존재하지 않습니다.")).isDelete();

    }

    @Override
    public MatchingResponseDTO getMatching(String matchingId) {

        Matching matching = matchingRepository.findByIdAndDelete(matchingId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        matching.increaseViews();

        matchingRepository.save(matching);

        return MatchingResponseDTO.builder()
                .id(matchingId)
                .nickname(matching.getUser().getNickname())
                .title(matching.getTitle())
                .content(matching.getContent())
                .views(matching.getViews())
                .createdAt(matching.getCreatedAt())
                .ploggingDate(matching.getPloggingDate())
                .total(matching.getTotal())
                .participants(matchingUserService.getParticipants(matchingId))
                .mountainName(matching.getMountain().getName())
                .mountainCode(matching.getMountain().getCode())
                .participate(matchingUserService.isJoin(matchingId))
                .close(matching.isClose())
                .build();

    }

    @Override
    public MatchingGetListWrapperResponseDTO getMatchingList(String mountainCode, int page) {

        page = Math.max(page, 1);

        List<Matching> matchingList = matchingRepository.findByPloggingDateGreaterThanEqualAndDeleteOrderByCreatedAtDesc(LocalDate.now(), false,
                        PageRequest.of(page - 1, 7))
                .orElse(null);

        List<MatchingGetListResponseDTO> matchingGetListResponseDTOList = new ArrayList<>();

        matchingList.forEach(matching -> {
                    if (mountainCode.equals(matching.getMountain().getCode())) {
                        matchingGetListResponseDTOList.add(
                                MatchingGetListResponseDTO.builder()
                                        .id(matching.getId())
                                        .nickname(matching.getUser().getNickname())
                                        .title(matching.getTitle())
                                        .createdAt(matching.getCreatedAt())
                                        .ploggingDate(matching.getPloggingDate())
                                        .total(matching.getTotal())
                                        .participants(matchingUserService.getParticipants(matching.getId()))
                                        .mountainName(matching.getMountain().getName())
                                        .close(isClose(matching.getId()))
                                        .build()
                        );
                    }
                }
        );

        return MatchingGetListWrapperResponseDTO.builder()
                .matchingGetListResponseDTOList(matchingGetListResponseDTOList)
                .build();

    }

    @Override
    public MatchingGetListWrapperResponseDTO getMyMatching(int page) {

        if (page < 1) page = 1;

        List<MatchingUser> myMatching = matchingUserRepository.findByUserIdAndDelete(
                userRepository.findByEmailAndDelete(
                                SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")).getId(),
                false, PageRequest.of(page - 1, 7)).orElse(null);

        List<Matching> matchingList = new ArrayList<>();

        myMatching.forEach(matchingUser -> matchingList.add(matchingUser.getMatching()));

        List<MatchingGetListResponseDTO> matchingGetListResponseDTOList = new ArrayList<>();

        matchingList.forEach(matching ->
                matchingGetListResponseDTOList.add(
                        MatchingGetListResponseDTO.builder()
                                .id(matching.getId())
                                .nickname(matching.getUser().getNickname())
                                .title(matching.getTitle())
                                .createdAt(matching.getCreatedAt())
                                .ploggingDate(matching.getPloggingDate())
                                .total(matching.getTotal())
                                .participants(matchingUserService.getParticipants(matching.getId()))
                                .mountainName(matching.getMountain().getName())
                                .close(isClose(matching.getId()))
                                .build()
                )
        );

        return MatchingGetListWrapperResponseDTO.builder()
                .matchingGetListResponseDTOList(matchingGetListResponseDTOList)
                .build();

    }

    @Override
    public void deleteMatching(String matchingId) {

        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매칭 글을 찾을 수 없습니다."));

        matching.changeDelete();
        matchingRepository.save(matching);

        List<MatchingUser> matchingUserList = matchingUserRepository.findByMatchingAndDelete(matching, false)
                .orElse(null);

        matchingUserList.forEach(matchingUser -> {
            matchingUser.changeDeleteTrue();

            matchingUserRepository.save(matchingUser);
        });

    }

}
