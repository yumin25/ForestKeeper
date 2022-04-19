package com.ssafy.forestkeeper.application.service.user;

import com.ssafy.forestkeeper.application.dto.request.user.UserRegisterPostDTO;

public interface UserService {

    // 회원가입
    Integer registerUser(UserRegisterPostDTO userRegisterPostDTO);

}
