package com.ssafy.forestkeeper.domain.repository.mountain;

import com.ssafy.forestkeeper.domain.dao.mountain.MountainVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MountainVisitRepository extends JpaRepository<MountainVisit, String> {

    Optional<MountainVisit> findByMountainCode(String mountainCode);

    List<MountainVisit> findTop5ByOrderByVisitorCountDesc();

}
