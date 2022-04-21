package com.ssafy.forestkeeper.application.service.user;


import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.domain.dao.user.User;

public interface UserService {
    Integer signUp(UserSignUpDTO userSignUpDTO);
    User getUser(String email);
    boolean isValidPassword(String password);
    boolean isValidName(String name);
    boolean isValidNickname(String nickname);
}
