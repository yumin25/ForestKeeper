package com.ssafy.api.application.service.matching;

import com.ssafy.api.application.dto.request.matching.MatchingModifyPatchDTO;
import com.ssafy.api.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.api.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.api.application.dto.response.matching.MatchingResponseDTO;

public interface MatchingService {

    // 글 작성
    void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO, String email);

    void modifyMatching(MatchingModifyPatchDTO matchingModifyPatchDTO);

    void closeMatching(String matchingId);

    boolean isFull(String matchingId);

    boolean isClose(String matchingId);

    void deleteMatching(String matchingId);

    boolean isDelete(String matchingId);

    // 매칭 글 조회
    MatchingResponseDTO getMatching(String matchingId);

    // 글 목록 조회
    MatchingGetListWrapperResponseDTO getMatchingList(int page);

    MatchingGetListWrapperResponseDTO getMyMatching(int page, String email);

//    // 글 검색
//    CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page);
//

}
