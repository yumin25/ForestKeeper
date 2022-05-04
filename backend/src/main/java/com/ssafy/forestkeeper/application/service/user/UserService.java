package com.ssafy.forestkeeper.application.service.user;


import com.ssafy.forestkeeper.application.dto.request.user.UserLoginDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserInfoDTO;

public interface UserService {
    Integer signUp(UserSignUpDTO userSignUpDTO);
    String login(UserLoginDTO userLoginDTO);
    String getUserEmail(String token);
    UserInfoDTO getUserDetail(String email);
    Integer modifyNickname(String nickname, String email);
    Integer modifyPassword(String past_password, String new_password, String email);
    boolean withdraw(String email);
    boolean checkNickname(String nickname);
    boolean checkEmail(String email);
    boolean isValidPassword(String password);
    boolean isValidName(String name);
    boolean isValidNickname(String nickname);
    public void updateUserImgPath(String originalName, String savedName);
    public void registerUserImgPath(String originalFileName, String savedFileName, String email);
}
