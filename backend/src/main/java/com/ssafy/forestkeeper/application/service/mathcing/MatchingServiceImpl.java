package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingResponseDTO;
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
        return matching.getMatchingUsers().size() == matching.getTotal();
    }

    @Override
    public boolean isClose(String matchingId) {

        return matchingRepository.findById(matchingId).get().isClosed();
    }

    @Override
    public MatchingResponseDTO getMatching(String matchingId) {
        Matching matching = matchingRepository.findById(matchingId)
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

        List<Matching> matchingList = matchingRepository.findAllByOrderByCreateTimeDesc(
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

//
//    @Override
//    public CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page) {
//
//        List<Community> communityList = new ArrayList<>();
//
//        switch (type) {
//            case "td":
//                communityList = communityRepository.findByCommunityCodeAndDeleteAndTitleContainingOrDescriptionContainingOrderByCreateTimeDesc(
//                                communityCode, false, keyword, keyword, PageRequest.of(page - 1, 6)
//                        )
//                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
//
//                break;
//            case "t":
//                communityList = communityRepository.findByCommunityCodeAndDeleteAndTitleContainingOrderByCreateTimeDesc(
//                                communityCode, false, keyword, PageRequest.of(page - 1, 6)
//                        )
//                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
//
//                break;
//            case "d":
//                communityList = communityRepository.findByCommunityCodeAndDeleteAndDescriptionContainingOrderByCreateTimeDesc(
//                                communityCode, false, keyword, PageRequest.of(page - 1, 6)
//                        )
//                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
//
//                break;
//            case "n":
//                communityList = communityRepository.findByCommunityCodeAndDeleteAndUserOrderByCreateTimeDesc(
//                                communityCode, false,
//                                userRepository.findByNicknameAndDelete(keyword, false)
//                                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")),
//                                PageRequest.of(page - 1, 6)
//                        )
//                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 작성한 글을 찾을 수 없습니다."));
//
//                break;
//        }
//
//        return convertCommunityListToDTO(communityList);
//
//    }
//
//    @Override
//    public CommunityResponseDTO getCommunity(String communityId) {
//
//        Community community = communityRepository.findByIdAndDelete(communityId, false)
//                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
//
//        community.increaseViews();
//
//        communityRepository.save(community);
//
//        return CommunityResponseDTO.builder()
//                .nickname(community.getUser().getNickname())
//                .title(community.getTitle())
//                .description(community.getDescription())
//                .views(community.getViews())
//                .createTime(community.getCreateTime())
//                .build();
//
//    }
//
//    @Override
//    public void modifyCommunity(CommunityModifyPatchDTO communityModifyPatchDTO) {
//
//        Community community = communityRepository.findByIdAndDelete(communityModifyPatchDTO.getCommunityId(), false)
//                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
//
//        community.changeCommunity(communityModifyPatchDTO.getTitle(), communityModifyPatchDTO.getDescription());
//
//        communityRepository.save(community);
//
//    }
//
//    @Override
//    public void deleteCommunity(String communityId) {
//
//        Community community = communityRepository.findById(communityId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
//
//        community.changeDelete();
//
//        communityRepository.save(community);
//
//        List<Comment> commentList = commentRepository.findByCommunityAndDeleteOrderByCreateTime(community, false)
//                .orElse(null);
//
//        commentList.forEach(comment -> {
//            comment.changeDelete();
//
//            commentRepository.save(comment);
//        });
//
//    }
//
//    private CommunityGetListWrapperResponseDTO convertCommunityListToDTO(List<Community> communityList) {
//
//        List<CommunityGetListResponseDTO> communityGetListResponseDTOList = new ArrayList<>();
//
//        communityList.forEach(community ->
//                communityGetListResponseDTOList.add(
//                        CommunityGetListResponseDTO.builder()
//                                .nickname(community.getUser().getNickname())
//                                .title(community.getTitle())
//                                .createTime(community.getCreateTime())
//                                .comments(commentRepository.countByCommunityAndDelete(community, false))
//                                .build()
//                )
//        );
//
//        return CommunityGetListWrapperResponseDTO.builder()
//                .communityGetListResponseDTOList(communityGetListResponseDTOList)
//                .build();
//
//    }

}
