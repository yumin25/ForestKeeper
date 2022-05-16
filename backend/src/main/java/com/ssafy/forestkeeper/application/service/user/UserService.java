package com.ssafy.forestkeeper.application.service.user;


import com.ssafy.forestkeeper.application.dto.request.user.UserLoginDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserInfoDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;

public interface UserService {

    Integer signUp(UserSignUpDTO userSignUpDTO);

    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);

    String getUserEmail(String token);

    UserInfoDTO getUserDetail();

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
