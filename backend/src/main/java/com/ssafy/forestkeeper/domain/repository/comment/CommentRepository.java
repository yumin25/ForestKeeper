package com.ssafy.forestkeeper.domain.repository.comment;

import com.ssafy.forestkeeper.domain.dao.community.Comment;
import com.ssafy.forestkeeper.domain.dao.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<List<Comment>> findByCommunityAndDeleteOrderByCreateTime(Community community, boolean delete);

    long countByCommunityAndDelete(Community community, boolean delete);

    Optional<Comment> findByIdAndDelete(String commentId, boolean delete);

}
