package com.ssafy.forestkeeper.application.service.community;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;

public interface CommunityService {

    // 글 작성
    void registerCommunity(CommunityRegisterRequestDTO communityRegisterRequestDTO);

    // 글 목록 조회
    CommunityGetListWrapperResponseDTO getCommunityList(String mountainId, CommunityCode communityCode, int page);

    // 글 검색
    CommunityGetListWrapperResponseDTO searchCommunity(String mountainId, CommunityCode communityCode, String type, String keyword, int page);

    // 글 조회
    CommunityResponseDTO getCommunity(String communityId);

    // 글 수정
    void modifyCommunity(CommunityModifyRequestDTO communityModifyRequestDTO);

    // 글 삭제
    void deleteCommunity(String communityId);

}
