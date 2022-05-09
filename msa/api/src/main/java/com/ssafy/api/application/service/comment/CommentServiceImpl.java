package com.ssafy.api.application.service.comment;

import com.ssafy.api.application.dto.request.comment.CommentModifyPatchDTO;
import com.ssafy.api.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.api.application.dto.response.comment.CommentGetListResponseDTO;
import com.ssafy.api.application.dto.response.comment.CommentGetListWrapperResponseDTO;
import com.ssafy.api.domain.dao.community.Comment;
import com.ssafy.api.domain.dao.community.Community;
import com.ssafy.api.domain.repository.comment.CommentRepository;
import com.ssafy.api.domain.repository.community.CommunityRepository;
import com.ssafy.api.domain.repository.user.UserRepository;
import com.ssafy.api.exception.CommentNotFoundException;
import com.ssafy.api.exception.CommunityNotFoundException;
import com.ssafy.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    @Override
    public void registerComment(CommentRegisterPostDTO commentRegisterPostDTO, String email) {

        Comment comment = Comment.builder()
                .description(commentRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(userRepository.findByEmailAndDelete(email, false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .community(communityRepository.findById(commentRegisterPostDTO.getCommunityId())
                        .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다.")))
                .build();

        commentRepository.save(comment);

    }

    @Override
    public CommentGetListWrapperResponseDTO getCommentList(String communityId) {

        Community community = communityRepository.findByIdAndDelete(communityId, false)
                .orElseThrow(() -> new CommunityNotFoundException("글 정보가 존재하지 않습니다."));

        List<CommentGetListResponseDTO> commentGetListResponseDTOList = new ArrayList<>();

        commentRepository.findByCommunityAndDeleteOrderByCreateTime(community, false).orElse(null)
                .forEach(comment ->
                        commentGetListResponseDTOList.add(
                                CommentGetListResponseDTO.builder()
                                        .nickname(comment.getUser().getNickname())
                                        .description(comment.getDescription())
                                        .createTime(comment.getCreateTime())
                                        .build()
                        )
                );

        return CommentGetListWrapperResponseDTO.builder()
                .commentGetListResponseDTOList(commentGetListResponseDTOList)
                .build();

    }

    @Override
    public void modifyComment(CommentModifyPatchDTO commentModifyPatchDTO) {

        Comment comment = commentRepository.findByIdAndDelete(commentModifyPatchDTO.getCommentId(), false)
                .orElseThrow(() -> new CommentNotFoundException("댓글 정보가 존재하지 않습니다."));

        comment.changeComment(commentModifyPatchDTO.getDescription());

        commentRepository.save(comment);

    }

    @Override
    public void deleteComment(String commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글 정보가 존재하지 않습니다."));

        comment.changeDelete();

        commentRepository.save(comment);

    }

}
