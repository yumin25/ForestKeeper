package com.ssafy.forestkeeper.application.service.comment;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import com.ssafy.forestkeeper.domain.repository.community.CommunityRepository;
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

    @Override
    public void registerComment(CommentRegisterPostDTO commentRegisterPostDTO) {

        Comment comment = Comment.builder()
                .description(commentRegisterPostDTO.getDescription())
                .createTime(LocalDateTime.now())
                .user(null)
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

        for (Comment comment : commentRepository.findByCommunityAndDeleteOrderByCreateTime(community, false).orElse(null)) {
            commentGetListResponseDTOList.add(
                    CommentGetListResponseDTO.builder()
//                            .nickname(comment.getUser().getNickname())
                            .description(comment.getDescription())
                            .createTime(comment.getCreateTime())
                            .build()
            );
        }

        return CommentGetListWrapperResponseDTO.builder()
                .commentGetListResponseDTOList(commentGetListResponseDTOList)
                .build();

    }

}
