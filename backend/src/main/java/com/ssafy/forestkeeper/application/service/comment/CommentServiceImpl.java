package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommunityRepository communityRepository;

    @Override
    public void registerComment(CommentRegisterPostDTO commentRegisterPostDTO) {

        Comment comment = Comment.builder()
                .description(commentRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(null)
                .community(communityRepository.findById(commentRegisterPostDTO.getCommunityId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")))
                .build();

        commentRepository.save(comment);

    }
}
