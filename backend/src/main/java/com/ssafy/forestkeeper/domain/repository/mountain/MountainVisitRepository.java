package com.ssafy.forestkeeper.domain.repository.mountain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.mountain.MountainVisit;

public interface MountainVisitRepository extends JpaRepository<MountainVisit, String>{

	Optional<MountainVisit> findByMountainCode(String mountainCode);
	List<MountainVisit> findTop5ByOrderByVisiterCountDesc();
}
