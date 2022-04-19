package com.ssafy.forestkeeper.domain.repository.user;

import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
