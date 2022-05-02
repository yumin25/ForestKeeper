package com.ssafy.forestkeeper.domain.repository.matching;

import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, String>{
    Optional<List<Matching>> findAllByOrderByCreateTimeDesc(Pageable pageable);
}
