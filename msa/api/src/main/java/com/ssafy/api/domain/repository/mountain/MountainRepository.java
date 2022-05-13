package com.ssafy.api.domain.repository.mountain;

import com.ssafy.api.domain.dao.mountain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, String>{
    Optional<Mountain> findByCode(String MountainCode);
    
    Optional<Mountain> findByName(String mountainName);
}
