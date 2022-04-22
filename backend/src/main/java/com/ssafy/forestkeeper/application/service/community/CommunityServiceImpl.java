package com.ssafy.forestkeeper.application.service.community;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityModifyPatchDTO;
import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterPostDTO;
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
    public void registerCommunity(CommunityRegisterPostDTO communityRegisterPostDTO) {

        Community community = Community.builder()
                .communityCode(communityRegisterPostDTO.getCommunityCode())
                .title(communityRegisterPostDTO.getTitle())
                .description(communityRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(userRepository.findByEmailAndDelete(
                        SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                .mountain(null)
//                .mountain(mountainRepository.findById(communityRegisterPostDTO.getMountainId())
//                        .orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다.")))
                .build();

        communityRepository.save(community);

    }

    @Override
    public CommunityGetListWrapperResponseDTO getCommunityList(CommunityCode communityCode, int page) {

        List<Community> communityList = communityRepository.findByCommunityCodeAndDeleteOrderByCreateTimeDesc(communityCode, false, PageRequest.of(page - 1, 10))
                .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

        return convertCommunityListToDTO(communityList);

    }

    @Override
    public CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page) {

        List<Community> communityList = new ArrayList<>();

        switch (type) {
            case "td":
                communityList = communityRepository.findByCommunityCodeAndDeleteAndTitleContainingOrDescriptionContainingOrderByCreateTimeDesc(
                                communityCode, false, keyword, keyword, PageRequest.of(page - 1, 10)
                        )
                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

                break;
            case "t":
                communityList = communityRepository.findByCommunityCodeAndDeleteAndTitleContainingOrderByCreateTimeDesc(
                                communityCode, false, keyword, PageRequest.of(page - 1, 10)
                        )
                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

                break;
            case "d":
                communityList = communityRepository.findByCommunityCodeAndDeleteAndDescriptionContainingOrderByCreateTimeDesc(
                                communityCode, false, keyword, PageRequest.of(page - 1, 10)
                        )
                        .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

                break;
            case "n":
                communityList = communityRepository.findByCommunityCodeAndDeleteAndUserOrderByCreateTimeDesc(
                                communityCode, false,
                                userRepository.findByNicknameAndDelete(keyword, false)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")),
                                PageRequest.of(page - 1, 10)
                        )
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 작성한 글을 찾을 수 없습니다."));

                break;
        }

        return convertCommunityListToDTO(communityList);

    }

    @Override
    public CommunityResponseDTO getCommunity(String communityId) {

        Community community = communityRepository.findByIdAndDelete(communityId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        community.increaseViews();

        communityRepository.save(community);

        return CommunityResponseDTO.builder()
                .nickname(community.getUser().getNickname())
                .title(community.getTitle())
                .description(community.getDescription())
                .views(community.getViews())
                .createTime(community.getCreateTime())
                .build();

    }

    @Override
    public void modifyCommunity(CommunityModifyPatchDTO communityModifyPatchDTO) {

        Community community = communityRepository.findByIdAndDelete(communityModifyPatchDTO.getCommunityId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        community.changeCommunity(communityModifyPatchDTO.getTitle(), communityModifyPatchDTO.getDescription());

        communityRepository.save(community);

    }

    @Override
    public void deleteCommunity(String communityId) {

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        community.changeDelete();

        communityRepository.save(community);

        List<Comment> commentList = commentRepository.findByCommunityAndDeleteOrderByCreateTime(community, false)
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
                                        .nickname(community.getUser().getNickname())
                                        .title(community.getTitle())
                                        .createTime(community.getCreateTime())
                                        .comments(commentRepository.countByCommunityAndDelete(community, false))
                                        .build()
                        )
        );

        return CommunityGetListWrapperResponseDTO.builder()
                .communityGetListResponseDTOList(communityGetListResponseDTOList)
                .build();

    }

}
