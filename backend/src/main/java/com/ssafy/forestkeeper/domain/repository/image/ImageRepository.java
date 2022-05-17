package com.ssafy.forestkeeper.domain.repository.image;

import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    Optional<Image> findByUser(User user);

    Optional<Image> findByPlogging(Plogging plogging);

    void deleteByUser(User user);

}
