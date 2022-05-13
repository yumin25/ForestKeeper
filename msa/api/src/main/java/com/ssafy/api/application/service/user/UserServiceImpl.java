package com.ssafy.api.application.service.user;

import com.ssafy.api.application.dto.response.user.UserInfoDTO;
import com.ssafy.api.domain.dao.image.Image;
import com.ssafy.api.domain.dao.user.User;
import com.ssafy.api.domain.repository.image.ImageRepository;
import com.ssafy.api.domain.repository.user.UserRepository;
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

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    @Override
    public String getUserEmail(String token) {
//        token = token.substring(7);
//        return jwtProvider.getUserAccount(token);
        return null;
    }

    @Override
    public UserInfoDTO getUserDetail(String email) {
        User user = userRepository.findByEmailAndDelete(email, false).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다"));
        Image image = imageRepository.findByUserId(user.getId()).orElse(null);
        String imagePath;
        if(image == null) imagePath = "";
        else imagePath = hosting + image.getSavedFileName();
        return UserInfoDTO.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imagePath(imagePath)
                .build();
    }

    @Override
    public Integer modifyNickname(String nickname, String email) {
        if (checkNickname(nickname)) return 4091;
        if (!isValidNickname(nickname)) return 4092;
        User user = userRepository.findUserByEmailAndDelete(email, false);
        user.setNickname(nickname);
        userRepository.save(user);
        return 201;
    }

    @Override
    public Integer modifyPassword(String past_password, String new_password, String email) {
        User user = userRepository.findUserByEmailAndDelete(email, false);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(past_password, user.getPassword())) return 4091;
        if (!isValidPassword(new_password)) return 4092;
        user.setPassword(encoder.encode(new_password));
        userRepository.save(user);
        return 201;
    }

    @Override
    public boolean withdraw(String email) {
        User user = userRepository.findUserByEmailAndDelete(email, false);
        user.setDelete(true);
        userRepository.save(user);
        return true;
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