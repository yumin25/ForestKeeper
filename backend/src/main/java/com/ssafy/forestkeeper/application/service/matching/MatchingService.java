package com.ssafy.forestkeeper.application.service.matching;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingModifyPatchDTO;
import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.matching.MatchingResponseDTO;

public interface MatchingService {

    // 글 작성
    void registerMatching(MatchingRegisterPostDTO matchingRegisterPostDTO);

    void modifyMatching(MatchingModifyPatchDTO matchingModifyPatchDTO);

    void closeMatching(String matchingId);

    boolean isFull(String matchingId);

    boolean isClose(String matchingId);

    void deleteMatching(String matchingId);

    boolean isDelete(String matchingId);

    // 매칭 글 조회
    MatchingResponseDTO getMatching(String matchingId);

    // 글 목록 조회
    MatchingGetListWrapperResponseDTO getMatchingList(String mountainCode, int page);

    MatchingGetListWrapperResponseDTO getMyMatching(int page);

//    // 글 검색
//    CommunityGetListWrapperResponseDTO searchCommunity(CommunityCode communityCode, String type, String keyword, int page);
//

}
