package com.ssafy.forestkeeper.domain.repository.matching;

import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import com.ssafy.forestkeeper.domain.dao.plogging.MatchingUser;
import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingUserRepository extends JpaRepository<MatchingUser, String> {

    Optional<List<MatchingUser>> findByMatchingAndDelete(Matching matching, boolean delete);

    Optional<List<MatchingUser>> findByUserIdAndDelete(String userId, boolean delete, Pageable pageable);

    Optional<MatchingUser> findByMatchingAndUser(Matching matching, User user);

}
