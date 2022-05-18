package com.ssafy.forestkeeper.domain.repository.plogging;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PloggingRepository extends JpaRepository<Plogging, String> {

    Optional<List<Plogging>> findByUserOrderByStartTimeDesc(User user, Pageable pageable);

    Optional<List<Plogging>> findByUser(User user);

    Optional<List<Plogging>> findByUserAndMountainOrderByStartTimeDesc(User user, Mountain mountain);

    Optional<List<Plogging>> findByMountain(Mountain mountain);

}
