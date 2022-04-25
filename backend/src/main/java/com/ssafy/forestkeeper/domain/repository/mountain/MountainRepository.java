package com.ssafy.forestkeeper.domain.repository.mountain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, String>{
    Mountain findByCode(String MountainCode);
}
