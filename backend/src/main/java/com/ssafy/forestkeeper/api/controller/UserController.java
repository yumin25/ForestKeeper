package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.user.UserLoginDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;
import com.ssafy.forestkeeper.application.service.user.UserService;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.security.util.JwtAuthenticationProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(value = "User API", tags = {"User"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserSignUpDTO userSignUpDTO) {
        try {
            Integer result = userService.signUp(userSignUpDTO);

            if(result == 4091) return ResponseEntity.status(409).body(BaseResponseDTO.of("이름 형식이 잘못되었습니다.", 409));
            else if (result == 4092) return ResponseEntity.status(409).body(BaseResponseDTO.of("닉네임 형식이 잘못되었습니다.", 409));
            else if (result == 4093) return ResponseEntity.status(409).body(BaseResponseDTO.of("비밀번호 형식이 잘못되었습니다.", 409));

            if (result == 409) {    //이메일 중복 검사 api
                return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 이메일로 가입된 계정이 이미 존재합니다.", 409));
            }
            else if (result == 409) {    //닉네임 중복 검사 api
                return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 닉네임으로 가입된 계정이 이미 존재합니다.", 409));
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
}
