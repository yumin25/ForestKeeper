package com.ssafy.auth.application.service.user;

import com.ssafy.auth.application.dto.request.user.UserLoginDTO;
import com.ssafy.auth.application.dto.request.user.UserSignUpDTO;

public interface UserService {
    Integer signUp(UserSignUpDTO userSignUpDTO);
    String login(UserLoginDTO userLoginDTO);
    boolean checkNickname(String nickname);
    boolean checkEmail(String email);
    boolean isValidPassword(String password);
    boolean isValidName(String name);
    boolean isValidNickname(String nickname);
    public void updateUserImgPath(String originalName, String savedName);
    public void registerUserImgPath(String originalFileName, String savedFileName, String email);
}
