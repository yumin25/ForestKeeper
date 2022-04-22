package com.ssafy.forestkeeper.application.service.user;

import com.ssafy.forestkeeper.application.dto.request.user.UserLoginDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.security.util.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtProvider;

    // 회원가입
    @Override
    public Integer signUp(UserSignUpDTO userDTO) {
        if(!isValidName(userDTO.getName())) return 4091;
        else if(!isValidNickname(userDTO.getNickname())) return 4092;
        else if(!isValidPassword(userDTO.getPassword())) return 4093;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(User.builder()
                .name(userDTO.getName())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles("ROLE_USER")
                .build());
        return 201;
    }

    //로그인
    @Override
    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmailEquals(userLoginDTO.getEmail());
        if (user == null) return "401";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(userLoginDTO.getPassword(), user.getPassword())) return "401";
        String token = jwtProvider.createToken(userLoginDTO.getEmail());
        System.out.println(token);
        return token;
    }

    @Override
    public boolean checkNickname(String nickname) {
        User user = userRepository.findByNicknameEquals(nickname);
        if(user == null) return false;
        return true;
    }

    @Override
    public boolean isValidPassword(String password) {
        Matcher m = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$").matcher(password); //영문자, 숫자, 특수문자 포함 8자 이상
        if(!m.matches()) return false;
        return true;
    }

    @Override
    public boolean isValidName(String name) {
        if(name.length() <2 || name.length()>10) return false;  //2자 이상 10자 이하
        if(!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name)) return false; //한글만 입력 허용
        return true;
    }

    @Override
    public boolean isValidNickname(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 10) return false;  //2자 이상 10자 이하
        if(!Pattern.matches("^[ㄱ-ㅎ가-힣0-9a-zA-Z]*$", nickname)) return false; //영어, 한글, 숫자만 입력 허용
        return true;
    }
}