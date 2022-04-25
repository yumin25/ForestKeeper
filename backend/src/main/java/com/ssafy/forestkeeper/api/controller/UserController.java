package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.user.UserLoginDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;
import com.ssafy.forestkeeper.application.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
            else if (result == 4094) return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 이메일로 가입된 계정이 이미 존재합니다.", 409));
            else if (result == 4095) return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 닉네임으로 가입된 계정이 이미 존재합니다.", 409));
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

    @ApiOperation(value = "닉네임 변경")
    @GetMapping("/modify/nickname")
    public ResponseEntity<?> modifyNickname(@RequestParam("nickname") String nickname, HttpServletRequest request) {
        String email = userService.getUserEmail(request.getHeader("X-AUTH-TOKEN"));
        Integer result = userService.modifyNickname(nickname, email);

        if(result == 201) return ResponseEntity.status(201).body(BaseResponseDTO.of("닉네임을 변경하였습니다.", 201));
        else if(result == 201) return ResponseEntity.status(201).body(BaseResponseDTO.of("닉네임을 변경하였습니다.", 201));
        else if(result == 4091) return ResponseEntity.status(409).body(BaseResponseDTO.of("사용 중인 닉네임입니다.", 409));
        else if(result == 4092) return ResponseEntity.status(409).body(BaseResponseDTO.of("닉네임 형식이 잘못되었습니다.", 409));
        else return ResponseEntity.status(500).body(BaseResponseDTO.of("닉네임 변경에 실패하였습니다.", 500));
    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/modify/password")
    public ResponseEntity<?> modifyPassword(@RequestBody Map<String,String> param, HttpServletRequest request) {
        String email = userService.getUserEmail(request.getHeader("X-AUTH-TOKEN"));
        String past_password = param.get("past_password");
        String new_password = param.get("new_password");
        Integer result = userService.modifyPassword(past_password, new_password, email);

        if(result == 201) return ResponseEntity.status(201).body(BaseResponseDTO.of("비밀번호를 변경하였습니다.", 201));
        if(result == 4091) return ResponseEntity.status(201).body(BaseResponseDTO.of("입력한 비밀번호가 일치하지 않습니다.", 409));
        if(result == 4092) return ResponseEntity.status(201).body(BaseResponseDTO.of("새로운 비밀번호의 형식이 올바르지 않습니다.", 409));
        return ResponseEntity.status(500).body(BaseResponseDTO.of("비밀번호 변경에 실패하였습니다.", 500));
    }

    @ApiOperation(value = "탈퇴")
    @GetMapping("/modify/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request) {
        String email = userService.getUserEmail(request.getHeader("X-AUTH-TOKEN"));
        if(userService.withdraw(email)) return ResponseEntity.status(201).body(BaseResponseDTO.of("탈퇴하였습니다.", 201));
        return ResponseEntity.status(500).body(BaseResponseDTO.of("탈퇴에 실패하였습니다.", 500));
    }
}
