package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentModifyPatchDTO;
import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;

public interface CommentService {

    // 댓글 작성
    void registerComment(CommentRegisterPostDTO commentRegisterPostDTO);

    // 댓글 리스트 조회
    CommentGetListWrapperResponseDTO getCommentList(String communityId);

    // 댓글 수정
    void modifyComment(CommentModifyPatchDTO commentModifyPatchDTO);

    // 댓글 삭제

}
