package com.ssafy.api.domain.repository.plogging;

import com.ssafy.api.domain.dao.plogging.Plogging;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PloggingRepository  extends JpaRepository<Plogging, String>{

	Optional<List<Plogging>> findByUserId(String userId, Pageable pageable);
	Optional<List<Plogging>> findByUserId(String userId);
	Optional<List<Plogging>> findByUserIdAndMountainId(String userId, String mountainId);
	Optional<List<Plogging>> findByMountainId(String mountainId);

}
