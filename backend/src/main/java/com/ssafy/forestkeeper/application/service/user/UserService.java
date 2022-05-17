package com.ssafy.forestkeeper.application.service.user;


import com.ssafy.forestkeeper.application.dto.request.user.UserLoginRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;

public interface UserService {

    Integer signUp(UserSignUpRequestDTO userSignUpRequestDTO);

    UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

    String getUserEmail(String token);

    UserInfoResponseDTO getUserDetail();

    Integer modifyNickname(String nickname);

    Integer modifyPassword(String past_password, String new_password);

    void withdraw();

    boolean checkNickname(String nickname);

    boolean checkEmail(String email);

    boolean isValidPassword(String password);

    boolean isValidName(String name);

    boolean isValidNickname(String nickname);

    void updateUserImgPath(String originalName, String savedName);

    void registerUserImgPath(String originalFileName, String savedFileName, String email);

}
