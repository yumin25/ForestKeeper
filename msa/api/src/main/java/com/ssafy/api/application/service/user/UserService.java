package com.ssafy.api.application.service.user;


import com.ssafy.api.application.dto.response.user.UserInfoDTO;

public interface UserService {
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
