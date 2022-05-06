package com.ssafy.api.domain.repository.matching;

import com.ssafy.api.domain.dao.plogging.Matching;
import com.ssafy.api.domain.dao.plogging.MatchingUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingUserRepository extends JpaRepository<MatchingUser, String> {

    Optional<List<MatchingUser>> findByMatchingAndDelete(Matching matching, boolean delete);
    Optional<List<MatchingUser>> findByUserIdAndDelete(String userId, boolean delete, Pageable pageable);
    Optional<MatchingUser> findByMatchingAndUserId(Matching matching, String userId);
}
