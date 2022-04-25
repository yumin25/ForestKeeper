package com.ssafy.forestkeeper.domain.repository.user;

import com.ssafy.forestkeeper.domain.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByEmailAndDelete(String email, boolean delete);

    User findUserByNicknameAndDelete(String nickname, boolean delete);

    Optional<User> findByNicknameAndDelete(String nickname, boolean delete);

    Optional<User> findByEmailAndDelete(String email, boolean delete);

}