package com.ssafy.forestkeeper.domain.repository.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.user.User;

public interface ImageRepository extends JpaRepository<Image, String>{

	Optional<Image> findByUserId(String userId);
	
	Optional<Image> findByPloggingId(String ploggingId);
	
	void deleteByUserId(String userId);
}
