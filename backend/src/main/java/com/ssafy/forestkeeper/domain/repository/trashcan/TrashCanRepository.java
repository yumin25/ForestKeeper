package com.ssafy.forestkeeper.domain.repository.trashcan;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.enums.RegionCode;

@Repository
public interface TrashCanRepository extends JpaRepository<TrashCan, String>{
	Optional<List<TrashCan>> findByRegionCode(RegionCode regionCode);
}
