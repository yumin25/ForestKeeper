package com.ssafy.forestkeeper.domain.repository.plogging;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

public interface PloggingRepository  extends JpaRepository<Plogging, String>{

}
