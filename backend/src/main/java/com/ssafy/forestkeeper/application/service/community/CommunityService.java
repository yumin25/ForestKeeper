package com.ssafy.forestkeeper.application.service.community;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;

public interface CommunityService {

    // 글 작성
    void registerCommunity(CommunityRegisterPostDTO communityRegisterPostDTO);

    // 글 목록 조회

    // 글 조회
    CommunityResponseDTO getCommunity(String communityId);

    // 글 수정

    // 글 삭제





}
