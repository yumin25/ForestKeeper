package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.CommentNotFoundException;
import com.ssafy.forestkeeper.exception.CommunityNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
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
    public void registerComment(CommentRegisterRequestDTO commentRegisterRequestDTO) {

        Comment comment = Comment.builder()
                .description(commentRegisterRequestDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .community(communityRepository.findById(commentRegisterRequestDTO.getCommunityId())
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
                                        .commentId(comment.getId())
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
    public void modifyComment(CommentModifyRequestDTO commentModifyRequestDTO) {

        Comment comment = commentRepository.findByIdAndDelete(commentModifyRequestDTO.getCommentId(), false)
                .orElseThrow(() -> new CommentNotFoundException("댓글 정보가 존재하지 않습니다."));

        comment.changeComment(commentModifyRequestDTO.getDescription());

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
