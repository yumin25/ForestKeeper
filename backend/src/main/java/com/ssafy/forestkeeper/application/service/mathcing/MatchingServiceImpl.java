package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingRepository;
import com.ssafy.forestkeeper.domain.repository.matching.MatchingUserRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MountainRepository mountainRepository;

    private final UserRepository userRepository;

    private final MatchingRepository matchingRepository;

    private final MatchingUserRepository matchingUserRepository;

    private final MatchingUserService matchingUserService;

    @Override
    public void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO) {

        Matching matching = Matching.builder()
            .title(matchingRegisterPostDTO.getTitle())
            .content(matchingRegisterPostDTO.getContent())
            .createTime(LocalDateTime.now())
            .ploggingDate(LocalDate.parse(matchingRegisterPostDTO.getPloggingDate()))
            .total(matchingRegisterPostDTO.getTotal())
            .user(userRepository.findByEmailAndDelete(
                    SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
            .mountain(mountainRepository.findByCode(matchingRegisterPostDTO.getMountainCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다.")))
            .build();

        matchingRepository.save(matching);

        matchingUserRepository.save(MatchingUser.builder().user(userRepository.findByEmailAndDelete(
                    SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
            .matching(matching).build());
    }

    @Override
    public void closeMatching(String matchingId) {
        Matching matching = matchingRepository.findById(matchingId).get();

        matching.setClosed(true);

        matchingRepository.save(matching);
    }

    @Override
    public boolean isFull(String matchingId) {
        Matching matching = matchingRepository.findById(matchingId).get();
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

        return matchingRepository.findById(matchingId).get().isClosed();
    }

    @Override
    public boolean isDelete(String matchingId) {

        return matchingRepository.findById(matchingId).get().isDelete();
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
            .createTime(matching.getCreateTime())
            .ploggingDate(matching.getPloggingDate())
            .total(matching.getTotal())
            .participant(matchingUserService.getParticipant(matchingId))
            .mountainName(matching.getMountain().getName())
            .mountainCode(matching.getMountain().getCode())
            .isClosed(matching.isClosed())
            .build();
    }

    @Override
    public MatchingGetListWrapperResponseDTO getMatchingList(int page) {

        if (page < 1) {
            page = 1;
        }

        List<Matching> matchingList = matchingRepository.findByDeleteOrderByCreateTimeDesc(false,
                PageRequest.of(page - 1, 6))
            .get();

        List<MatchingGetListResponseDTO> matchingGetListResponseDTOList = new ArrayList<>();

        matchingList.forEach(matching ->
            matchingGetListResponseDTOList.add(
                MatchingGetListResponseDTO.builder()
                    .id(matching.getId())
                    .nickname(matching.getUser().getNickname())
                    .title(matching.getTitle())
                    .createTime(matching.getCreateTime())
                    .ploggingDate(matching.getPloggingDate())
                    .total(matching.getTotal())
                    .participant(matchingUserService.getParticipant(matching.getId()))
                    .mountainName(matching.getMountain().getName())
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

        List<MatchingUser> matchingUserList = matchingUserRepository.findByMatchingAndDelete(
                matching, false)
            .orElse(null);

        matchingUserList.forEach(matchingUser -> {
            matchingUser.changeDelete();

            matchingUserRepository.save(matchingUser);
        });

    }
}
