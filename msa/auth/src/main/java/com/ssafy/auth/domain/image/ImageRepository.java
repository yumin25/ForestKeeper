package com.ssafy.auth.domain.image;

import com.ssafy.auth.domain.dao.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String>{

	Optional<Image> findByUserId(String userId);
	
	Optional<Image> findByPloggingId(String ploggingId);
	
	void deleteByUserId(String userId);
}
