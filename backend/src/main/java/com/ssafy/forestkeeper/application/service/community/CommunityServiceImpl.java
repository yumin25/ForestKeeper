package com.ssafy.forestkeeper.application.service.community;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.CommunityNotFoundException;
import com.ssafy.forestkeeper.exception.MountainNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final MountainRepository mountainRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Override
    public void registerCommunity(CommunityRegisterRequestDTO communityRegisterRequestDTO) {

        Community community = Community.builder()
                .communityCode(communityRegisterRequestDTO.getCommunityCode())
                .title(communityRegisterRequestDTO.getTitle())
                .content(communityRegisterRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .mountain(mountainRepository.findById(communityRegisterRequestDTO.getMountainId())
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다.")))
                .build();

        communityRepository.save(community);

    }

    @Override
    public CommunityGetListWrapperResponseDTO getCommunityList(String mountainId, CommunityCode communityCode, int page) {

        List<Community> communityList = communityRepository.findByMountainIdAndCommunityCodeAndDeleteOrderByCreatedAtDesc(mountainId, communityCode, false, PageRequest.of(page - 1, 7))
                .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

        return convertCommunityListToDTO(communityList);

    }

    @Override
    public CommunityGetListWrapperResponseDTO searchCommunity(String mountainId, CommunityCode communityCode, String type, String keyword, int page) {

        List<Community> communityList = new ArrayList<>();

        switch (type) {
            case "td":
                communityList = communityRepository.findByMountainAndCommunityCodeAndDeleteAndTitleContainingOrContentContainingOrderByCreatedAtDesc(
                                mountainRepository.findById(mountainId)
                                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다.")),
                                communityCode, false, keyword, keyword, PageRequest.of(page - 1, 7)
                        )
                        .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

                break;
            case "t":
                communityList = communityRepository.findByMountainIdAndCommunityCodeAndDeleteAndTitleContainingOrderByCreatedAtDesc(
                                mountainId, communityCode, false, keyword, PageRequest.of(page - 1, 7)
                        )
                        .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

                break;
            case "d":
                communityList = communityRepository.findByMountainIdAndCommunityCodeAndDeleteAndContentContainingOrderByCreatedAtDesc(
                                mountainId, communityCode, false, keyword, PageRequest.of(page - 1, 7)
                        )
                        .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

                break;
            case "n":
                communityList = communityRepository.findByMountainIdAndCommunityCodeAndDeleteAndUserOrderByCreatedAtDesc(
                                mountainId, communityCode, false,
                                userRepository.findByNicknameAndDelete(keyword, false)
                                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")),
                                PageRequest.of(page - 1, 7)
                        )
                        .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

                break;
        }

        return convertCommunityListToDTO(communityList);

    }

    @Override
    public CommunityResponseDTO getCommunity(String communityId) {

        Community community = communityRepository.findByIdAndDelete(communityId, false)
                .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

        community.increaseViews();

        communityRepository.save(community);

        return CommunityResponseDTO.builder()
                .nickname(community.getUser().getNickname())
                .title(community.getTitle())
                .content(community.getContent())
                .views(community.getViews())
                .createdAt(community.getCreatedAt())
                .build();

    }

    @Override
    public void modifyCommunity(CommunityModifyRequestDTO communityModifyRequestDTO) {

        Community community = communityRepository.findByIdAndDelete(communityModifyRequestDTO.getCommunityId(), false)
                .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

        community.changeCommunity(communityModifyRequestDTO.getTitle(), communityModifyRequestDTO.getContent());

        communityRepository.save(community);

    }

    @Override
    public void deleteCommunity(String communityId) {

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

        community.changeDelete();

        communityRepository.save(community);

        List<Comment> commentList = commentRepository.findByCommunityAndDeleteOrderByCreatedAt(community, false)
                .orElse(null);

        commentList.forEach(comment -> {
            comment.changeDelete();

            commentRepository.save(comment);
        });

    }

    private CommunityGetListWrapperResponseDTO convertCommunityListToDTO(List<Community> communityList) {

        List<CommunityGetListResponseDTO> communityGetListResponseDTOList = new ArrayList<>();

        communityList.forEach(community ->
                communityGetListResponseDTOList.add(
                        CommunityGetListResponseDTO.builder()
                                .communityId(community.getId())
                                .nickname(community.getUser().getNickname())
                                .title(community.getTitle())
                                .createdAt(community.getCreatedAt())
                                .comments(commentRepository.countByCommunityAndDelete(community, false))
                                .build()
                )
        );

        return CommunityGetListWrapperResponseDTO.builder()
                .communityGetListResponseDTOList(communityGetListResponseDTOList)
                .build();

    }

}
