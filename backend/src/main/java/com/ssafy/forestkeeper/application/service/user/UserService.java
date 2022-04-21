package com.ssafy.forestkeeper.application.service.user;


import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;

public interface UserService {
    Integer signUp(UserSignUpDTO userSignUpDTO);
    boolean isValidPassword(String password);
    boolean isValidName(String name);
    boolean isValidNickname(String nickname);
}
