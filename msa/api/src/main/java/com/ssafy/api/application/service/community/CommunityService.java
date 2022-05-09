package com.ssafy.api.application.service.community;

import com.ssafy.api.application.dto.request.community.CommunityModifyPatchDTO;
import com.ssafy.api.application.dto.request.community.CommunityRegisterPostDTO;
import com.ssafy.api.application.dto.response.community.CommunityGetListWrapperResponseDTO;
import com.ssafy.api.application.dto.response.community.CommunityResponseDTO;
import com.ssafy.api.domain.enums.CommunityCode;

public interface CommunityService {

    // 글 작성
    void registerCommunity(CommunityRegisterPostDTO communityRegisterPostDTO, String email);

    // 글 목록 조회
    CommunityGetListWrapperResponseDTO getCommunityList(CommunityCode communityCode, int page);

    // 글 검색
    CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page);

    // 글 조회
    CommunityResponseDTO getCommunity(String communityId);

    // 글 수정
    void modifyCommunity(CommunityModifyPatchDTO communityModifyPatchDTO);

    // 글 삭제
    void deleteCommunity(String communityId);

}
