package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;

public interface MatchingService {

    // 글 작성
    void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO);

//    // 글 목록 조회
//    CommunityGetListWrapperResponseDTO getCommunityList(CommunityCode communityCode, int page);
//
//    // 글 검색
//    CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page);
//
//    // 글 조회
//    CommunityResponseDTO getCommunity(String communityId);
//
//    // 글 수정
//    void modifyCommunity(CommunityModifyPatchDTO communityModifyPatchDTO);
//
//    // 글 삭제
//    void deleteCommunity(String communityId);

}
