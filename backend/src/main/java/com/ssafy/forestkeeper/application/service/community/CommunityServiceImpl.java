package com.ssafy.forestkeeper.application.service.community;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final MountainRepository mountainRepository;

    @Override
    public void registerCommunity(CommunityRegisterPostDTO communityRegisterPostDTO) {

        Community community = Community.builder()
                .communityCode(communityRegisterPostDTO.getCommunityCode())
                .title(communityRegisterPostDTO.getTitle())
                .description(communityRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(null)
                .mountain(null)
//                .mountain(mountainRepository.findById(communityRegisterPostDTO.getMountainId())
//                        .orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다.")))
                .build();

        communityRepository.save(community);

    }

    @Override
    public CommunityResponseDTO getCommunity(String communityId) {

        Community community = communityRepository.findByIdAndDelete(communityId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        community.increaseViews();

        communityRepository.save(community);

        return CommunityResponseDTO.builder()
                .title(community.getTitle())
                .description(community.getDescription())
                .views(community.getViews())
                .createTime(community.getCreateTime())
                .build();

    }

}
