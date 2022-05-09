package com.ssafy.auth.api.controller;

import com.ssafy.auth.application.dto.request.user.UserLoginDTO;
import com.ssafy.auth.application.dto.request.user.UserSignUpDTO;
import com.ssafy.auth.application.dto.response.BaseResponseDTO;
import com.ssafy.auth.application.dto.response.UserLoginResponseDTO;
import com.ssafy.auth.application.service.user.UserService;
import com.ssafy.auth.application.service.s3.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Auth API", tags = {"Auth"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

    @ApiOperation(value = "회원가입")
    @PostMapping
    public ResponseEntity<?> register(@ModelAttribute UserSignUpDTO userSignUpDTO) {
        try {
            Integer result = userService.signUp(userSignUpDTO);

            if(result == 4091) return ResponseEntity.status(409).body(BaseResponseDTO.of("이름 형식이 잘못되었습니다.", 409));
            else if (result == 4092) return ResponseEntity.status(409).body(BaseResponseDTO.of("닉네임 형식이 잘못되었습니다.", 409));
            else if (result == 4093) return ResponseEntity.status(409).body(BaseResponseDTO.of("비밀번호 형식이 잘못되었습니다.", 409));
            else if (result == 4094) return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 이메일로 가입된 계정이 이미 존재합니다.", 409));
            else if (result == 4095) return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 닉네임으로 가입된 계정이 이미 존재합니다.", 409));

            if(userSignUpDTO.getImage() != null) {
                String savedFileName = s3Service.uploadFileToS3("user", userSignUpDTO.getImage());
                userService.registerUserImgPath(userSignUpDTO.getImage().getOriginalFilename(), savedFileName, userSignUpDTO.getEmail());
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("회원가입에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("회원가입에 성공했습니다.", 201));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO){
        String result;
        try{
            result = userService.login(userLoginDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(BaseResponseDTO.of("로그인에 실패하였습니다.", 500));
        }
        if (result.equals("401")) return ResponseEntity.status(401).body(BaseResponseDTO.of("아이디 또는 비밀번호를 잘못 입력하였습니다.", 401));
        return ResponseEntity.status(200).body(UserLoginResponseDTO.of(result, "로그인 하였습니다.", 200));
    }

    @ApiOperation(value = "닉네임 중복 확인")
    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        if(userService.checkNickname(nickname)){
            return ResponseEntity.status(409).body(BaseResponseDTO.of("사용 중인 닉네임입니다.", 409));
        }
        return ResponseEntity.status(200).body(BaseResponseDTO.of("사용 가능한 닉네임입니다.", 200));
    }

    @ApiOperation(value = "이메일 중복 확인")
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        if(userService.checkEmail(email)){
            return ResponseEntity.status(409).body(BaseResponseDTO.of("사용 중인 이메일입니다.", 409));
        }
        return ResponseEntity.status(200).body(BaseResponseDTO.of("사용 가능한 이메일입니다.", 200));
    }
}
