package com.ssafy.forestkeeper.domain.repository.plogging;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

public interface PloggingRepository  extends JpaRepository<Plogging, String>{

	Optional<List<Plogging>> findByUserIdOrderByStartTimeDesc(String userId, Pageable pageable);
	Optional<List<Plogging>> findByUserId(String userId);
	Optional<List<Plogging>> findByUserIdAndMountainIdOrderByStartTimeDesc(String userId, String mountainId);
	Optional<List<Plogging>> findByMountainId(String mountainId);

}
