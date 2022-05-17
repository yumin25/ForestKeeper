package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;

public interface CommentService {

    // 댓글 작성
    void registerComment(CommentRegisterRequestDTO commentRegisterRequestDTO);

    // 댓글 리스트 조회
    CommentGetListWrapperResponseDTO getCommentList(String communityId);

    // 댓글 수정
    void modifyComment(CommentModifyRequestDTO commentModifyRequestDTO);

    // 댓글 삭제
    void deleteComment(String commentId);

}
