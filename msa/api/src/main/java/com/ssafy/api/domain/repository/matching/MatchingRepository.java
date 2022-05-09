package com.ssafy.api.domain.repository.matching;

import com.ssafy.api.domain.dao.plogging.Matching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, String>{

    Optional<Matching> findByIdAndDelete(String matchingId, boolean delete);

    Optional<List<Matching>> findByDeleteOrderByCreateTimeDesc(boolean delete, Pageable pageable);
}
