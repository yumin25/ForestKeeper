package com.ssafy.api.application.service.comment;

import com.ssafy.api.application.dto.request.comment.CommentModifyPatchDTO;
import com.ssafy.api.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.api.application.dto.response.comment.CommentGetListWrapperResponseDTO;

public interface CommentService {

    // 댓글 작성
    void registerComment(CommentRegisterPostDTO commentRegisterPostDTO, String email);

    // 댓글 리스트 조회
    CommentGetListWrapperResponseDTO getCommentList(String communityId);

    // 댓글 수정
    void modifyComment(CommentModifyPatchDTO commentModifyPatchDTO);

    // 댓글 삭제
    void deleteComment(String commentId);

}
