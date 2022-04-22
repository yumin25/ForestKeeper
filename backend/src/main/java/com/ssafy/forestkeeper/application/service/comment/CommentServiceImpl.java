package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentModifyPatchDTO;
import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void registerComment(CommentRegisterPostDTO commentRegisterPostDTO) {

        Comment comment = Comment.builder()
                .description(commentRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                .community(communityRepository.findById(commentRegisterPostDTO.getCommunityId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다.")))
                .build();

        commentRepository.save(comment);

    }

    @Override
    public CommentGetListWrapperResponseDTO getCommentList(String communityId) {

        Community community = communityRepository.findByIdAndDelete(communityId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        comment.changeComment(commentModifyPatchDTO.getDescription());

        commentRepository.save(comment);

    }

    @Override
    public void deleteComment(String commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        comment.changeDelete();

        commentRepository.save(comment);

    }

}
