package com.ssafy.forestkeeper.domain.repository.mountain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, String>{
    Mountain findByCode(String MountainCode);
    
    Mountain findByName(String mountainName);
}
