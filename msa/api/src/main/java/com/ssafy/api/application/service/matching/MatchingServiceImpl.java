package com.ssafy.api.application.service.matching;

import com.ssafy.api.application.dto.request.matching.MatchingModifyPatchDTO;
import com.ssafy.api.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.api.application.dto.response.matching.MatchingGetListResponseDTO;
import com.ssafy.api.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.api.application.dto.response.matching.MatchingResponseDTO;
import com.ssafy.api.domain.dao.plogging.Matching;
import com.ssafy.api.domain.dao.plogging.MatchingUser;
import com.ssafy.api.domain.repository.matching.MatchingRepository;
import com.ssafy.api.domain.repository.matching.MatchingUserRepository;
import com.ssafy.api.domain.repository.mountain.MountainRepository;
import com.ssafy.api.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO, String email) {

        Matching matching = Matching.builder()
            .title(matchingRegisterPostDTO.getTitle())
            .content(matchingRegisterPostDTO.getContent())
            .createTime(LocalDateTime.now())
            .ploggingDate(LocalDate.parse(matchingRegisterPostDTO.getPloggingDate()))
            .total(matchingRegisterPostDTO.getTotal())
            .user(userRepository.findByEmailAndDelete(email, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
            .mountain(mountainRepository.findByCode(matchingRegisterPostDTO.getMountainCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다.")))
            .build();

        matchingRepository.save(matching);

        matchingUserRepository.save(MatchingUser.builder().user(userRepository.findByEmailAndDelete(email, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
            .matching(matching).build());
    }

    @Override
    public void modifyMatching(MatchingModifyPatchDTO matchingModifyPatchDTO) {
        Matching matching = matchingRepository.findByIdAndDelete(
                matchingModifyPatchDTO.getMatchingId(), false)
            .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        matching.changeMatching(matchingModifyPatchDTO.getTitle(),
            matchingModifyPatchDTO.getContent(), LocalDate.parse(matchingModifyPatchDTO.getPloggingDate()),
            matchingModifyPatchDTO.getTotal(),
            mountainRepository.findByCode(matchingModifyPatchDTO.getMountainCode()).get());

        matchingRepository.save(matching);
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
    public MatchingGetListWrapperResponseDTO getMyMatching(int page, String email) {

        if (page < 1) {
            page = 1;
        }

        List<MatchingUser> myMatching = matchingUserRepository.findByUserIdAndDelete(
            userRepository.findByEmailAndDelete(email, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")).getId(),
            false, PageRequest.of(page - 1, 6)).get();

        List<Matching> matchingList = new ArrayList<>();

        myMatching.forEach(matchingUser -> matchingList.add(matchingUser.getMatching()));

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
