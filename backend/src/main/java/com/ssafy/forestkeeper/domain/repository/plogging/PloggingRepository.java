package com.ssafy.forestkeeper.domain.repository.plogging;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

public interface PloggingRepository  extends JpaRepository<Plogging, String>{

	Optional<List<Plogging>> findByUserId(String userId, Pageable pageable);
	Optional<List<Plogging>> findByUserId(String userId);
	Optional<List<Plogging>> findByUserIdAndMountainId(String userId, String mountainId);

}
