package com.ssafy.forestkeeper.application.service.user;

import com.ssafy.forestkeeper.application.dto.request.user.UserLoginRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.enums.UserCode;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import com.ssafy.forestkeeper.security.util.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    // 회원가입
    @Override
    public Integer signUp(UserSignUpRequestDTO userDTO) {

        if (!isValidName(userDTO.getName())) return 4091;
        else if (!isValidNickname(userDTO.getNickname())) return 4092;
        else if (!isValidPassword(userDTO.getPassword())) return 4093;
        else if (checkEmail(userDTO.getEmail())) return 4094;
        else if (checkNickname(userDTO.getNickname())) return 4095;

        userRepository.save(User.builder()
                .userCode(UserCode.USER)
                .name(userDTO.getName())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build());

        return 201;

    }

    // 로그인
    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword())
        );

        return UserLoginResponseDTO.builder()
                .accessToken(jwtAuthenticationProvider.createToken(authentication))
                .build();

    }

    @Override
    public String getUserEmail(String token) {

        return jwtAuthenticationProvider.getUserAccount(token.substring(7));

    }

    @Override
    public UserInfoResponseDTO getUserDetail() {

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        Image image = imageRepository.findByUser(user).orElse(null);

        String imagePath;
        String thumbnailPath;

        if (image == null) {
            imagePath = "";
            thumbnailPath = "";
        } else {
            imagePath = hosting + "user/" + image.getSavedFileName();
            thumbnailPath = hosting + "thumb/" + image.getSavedFileName();
        }

        return UserInfoResponseDTO.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imagePath(imagePath)
                .thumbnailPath(thumbnailPath)
                .build();

    }

    @Override
    public Integer modifyNickname(String nickname) {

        if (checkNickname(nickname)) return 4091;

        if (!isValidNickname(nickname)) return 4092;

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        user.changeNickname(nickname);

        userRepository.save(user);

        return 200;

    }

    @Override
    public Integer modifyPassword(String currentPassword, String newPassword) {

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) return 4091;

        if (!isValidPassword(newPassword)) return 4092;

        user.changePassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return 200;

    }

    @Override
    public void withdraw() {

        User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."));

        user.changeDelete();

        userRepository.save(user);

    }


    @Override
    public boolean checkNickname(String nickname) {

        return userRepository.findByNicknameAndDelete(nickname, false).isPresent();

    }

    @Override
    public boolean checkEmail(String email) {

        return userRepository.findByEmailAndDelete(email, false).isPresent();

    }

    @Override
    public boolean isValidPassword(String password) {

        Matcher m = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$").matcher(password); // 영문자, 숫자, 특수문자 포함 8자 이상

        return m.matches();

    }

    @Override
    public boolean isValidName(String name) {

        if (name.length() < 2 || name.length() > 10) return false;  // 2자 이상 10자 이하

        return Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name); // 한글만 입력 허용

    }

    @Override
    public boolean isValidNickname(String nickname) {

        if (nickname.length() < 2 || nickname.length() > 10) return false;  // 2자 이상 10자 이하

        return Pattern.matches("^[ㄱ-ㅎ가-힣0-9a-zA-Z]*$", nickname); // 영어, 한글, 숫자만 입력 허용

    }

    @Override
    public void updateUserImgPath(String originalFileName, String savedFileName) {

        Image image = imageRepository.findByUser(
                userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."))
        ).orElse(null);

        if (image != null) {
            image.changeFileName(originalFileName, savedFileName);

            imageRepository.save(image);
        } else {
            imageRepository.save(Image.builder()
                    .originalFileName(originalFileName)
                    .savedFileName(savedFileName)
                    .user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                            .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                    .build());
        }

    }

    @Override
    public void registerUserImgPath(String originalFileName, String savedFileName, String email) {

        imageRepository.save(Image.builder()
                .originalFileName(originalFileName)
                .savedFileName(savedFileName)
                .user(userRepository.findByEmailAndDelete(email, false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .build());

    }

}