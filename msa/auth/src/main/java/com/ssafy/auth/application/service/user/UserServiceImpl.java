package com.ssafy.auth.application.service.user;

import com.ssafy.auth.application.dto.request.user.UserLoginDTO;
import com.ssafy.auth.application.dto.request.user.UserSignUpDTO;
import com.ssafy.auth.domain.dao.image.Image;
import com.ssafy.auth.domain.dao.user.User;
import com.ssafy.auth.domain.enums.UserCode;
import com.ssafy.auth.domain.image.ImageRepository;
import com.ssafy.auth.domain.repository.UserRepository;
import com.ssafy.auth.security.util.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final JwtAuthenticationProvider jwtProvider;

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    // 회원가입
    @Override
    public Integer signUp(UserSignUpDTO userDTO) {
        if (!isValidName(userDTO.getName())) return 4091;
        else if (!isValidNickname(userDTO.getNickname())) return 4092;
        else if (!isValidPassword(userDTO.getPassword())) return 4093;
        else if (checkEmail(userDTO.getEmail())) return 4094;
        else if (checkNickname(userDTO.getNickname())) return 4095;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(User.builder()
                .userCode(UserCode.USER)
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
        User user = userRepository.findUserByEmailAndDelete(userLoginDTO.getEmail(), false);
        if (user == null) return "401";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(userLoginDTO.getPassword(), user.getPassword())) return "401";
        String token = jwtProvider.createToken(userLoginDTO.getEmail());
        System.out.println(token);
        return token;
    }

    @Override
    public boolean checkNickname(String nickname) {
        User user = userRepository.findUserByNicknameAndDelete(nickname, false);
        if (user == null) return false;
        return true;
    }

    @Override
    public boolean checkEmail(String email) {
        User user = userRepository.findUserByEmailAndDelete(email, false);
        if (user == null) return false;
        return true;
    }

    @Override
    public boolean isValidPassword(String password) {
        Matcher m = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$").matcher(password); //영문자, 숫자, 특수문자 포함 8자 이상
        if (!m.matches()) return false;
        return true;
    }

    @Override
    public boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 10) return false;  //2자 이상 10자 이하
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name)) return false; //한글만 입력 허용
        return true;
    }

    @Override
    public boolean isValidNickname(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 10) return false;  //2자 이상 10자 이하
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣0-9a-zA-Z]*$", nickname)) return false; //영어, 한글, 숫자만 입력 허용
        return true;
    }

    @Override
    public void updateUserImgPath(String originalFileName, String savedFileName) {
        Image image = imageRepository.findByUserId(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get().getId()).orElse(null);

        if(image != null) {
            image.setOriginalFileName(originalFileName);
            image.setSavedFileName(savedFileName);
            imageRepository.save(image);
        }else {
            imageRepository.save(Image.builder()
                    .originalFileName(originalFileName)
                    .savedFileName(savedFileName)
                    .user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get())
                    .build());
        }
    }

    @Override
    public void registerUserImgPath(String originalFileName, String savedFileName, String email) {
        imageRepository.save(Image.builder()
                .originalFileName(originalFileName)
                .savedFileName(savedFileName)
                .user(userRepository.findByEmailAndDelete(email,false).get())
                .build());
    }
}
