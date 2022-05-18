package com.ssafy.forestkeeper.domain.repository.matching;

import com.ssafy.forestkeeper.domain.dao.plogging.Matching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, String> {

    Optional<Matching> findByIdAndDelete(String matchingId, boolean delete);

    Optional<List<Matching>> findByPloggingDateGreaterThanEqualAndDeleteOrderByCreateTimeDesc(LocalDate today, boolean delete, Pageable pageable);

}
