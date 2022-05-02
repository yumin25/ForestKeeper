package com.ssafy.forestkeeper.application.service.mathcing;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingResponseDTO;

public interface MatchingService {

    // 글 작성
    void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO);

    void closeMatching(String matchingId);

    boolean isFull(String matchingId);

    boolean isClose(String matchingId);

    // 매칭 글 조회
    MatchingResponseDTO getMatching(String matchingId);

    // 글 목록 조회
    MatchingGetListWrapperResponseDTO getMatchingList(int page);

//    // 글 검색
//    CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page);
//

//    // 글 수정
//    void modifyCommunity(CommunityModifyPatchDTO communityModifyPatchDTO);
//
//    // 글 삭제
//    void deleteCommunity(String communityId);

}
