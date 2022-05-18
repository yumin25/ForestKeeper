package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.user.PasswordModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserLoginRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.user.UserSignUpRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserLoginResponseDTO;
import com.ssafy.forestkeeper.application.service.s3.AwsS3Service;
import com.ssafy.forestkeeper.application.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "User API", tags = {"User"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "회원가입")
    @PostMapping
    public ResponseEntity<?> signup(@RequestPart(value = "dto") UserSignUpRequestDTO userSignUpRequestDTO,
                                    @RequestPart(value = "image", required = false) MultipartFile multipartFile) {

        Integer result = userService.signUp(userSignUpRequestDTO);

        if (result == 4091)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("이름 형식이 잘못되었습니다.", 409));
        else if (result == 4092)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("닉네임 형식이 잘못되었습니다.", 409));
        else if (result == 4093)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("비밀번호 형식이 잘못되었습니다.", 409));
        else if (result == 4094)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("해당 이메일로 가입된 계정이 이미 존재합니다.", 409));
        else if (result == 4095)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("해당 닉네임으로 가입된 계정이 이미 존재합니다.", 409));

        if (multipartFile != null) {
            String savedFileName = awsS3Service.uploadFileToS3("user", multipartFile);

            userService.registerUserImgPath(multipartFile.getOriginalFilename(), savedFileName, userSignUpRequestDTO.getEmail());
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("회원가입에 성공했습니다.", 201));

    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        return ResponseEntity.status(200).body(UserLoginResponseDTO.of("로그인에 성공했습니다.", 200, userService.login(userLoginRequestDTO)));

    }

    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo() {

        return ResponseEntity.ok(
                UserInfoResponseDTO.of("사용자 정보 조회에 성공하였습니다.", 200, userService.getUserDetail())
        );

    }

    @ApiOperation(value = "닉네임 중복 확인")
    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {

        if (userService.checkNickname(nickname))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("사용 중인 닉네임입니다.", 409));

        return ResponseEntity.ok(BaseResponseDTO.of("사용 가능한 닉네임입니다.", 200));

    }

    @ApiOperation(value = "이메일 중복 확인")
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {

        if (userService.checkEmail(email))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("사용 중인 이메일입니다.", 409));

        return ResponseEntity.ok(BaseResponseDTO.of("사용 가능한 이메일입니다.", 200));

    }

    @ApiOperation(value = "닉네임 변경")
    @PatchMapping("/modify/nickname")
    public ResponseEntity<?> modifyNickname(@RequestParam("nickname") String nickname) {

        Integer result = userService.modifyNickname(nickname);

        if (result == 4091)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("사용 중인 닉네임입니다.", 409));
        else if (result == 4092)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("닉네임 형식이 잘못되었습니다.", 409));

        return ResponseEntity.ok(BaseResponseDTO.of("닉네임을 변경하였습니다.", 200));

    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/modify/password")
    public ResponseEntity<?> modifyPassword(@RequestBody PasswordModifyRequestDTO passwordModifyRequestDTO) {

        Integer result = userService.modifyPassword(passwordModifyRequestDTO.getCurrentPassword(), passwordModifyRequestDTO.getNewPassword());

        if (result == 4091)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("입력한 비밀번호가 일치하지 않습니다.", 409));
        if (result == 4092)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of("새로운 비밀번호의 형식이 올바르지 않습니다.", 409));

        return ResponseEntity.ok(BaseResponseDTO.of("비밀번호를 변경하였습니다.", 200));

    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/modify/withdraw")
    public ResponseEntity<?> withdraw() {

        userService.withdraw();

        return ResponseEntity.ok(BaseResponseDTO.of("탈퇴하였습니다.", 200));

    }

    @ApiOperation(value = "회원 프로필 사진 수정")
    @PatchMapping("/modify/profile")
    public ResponseEntity<?> updateUserImage(
            @ApiParam(value = "회원 프로필 사진.", required = true) @RequestBody MultipartFile image
    ) {

        String savedFileName = awsS3Service.uploadFileToS3("user", image);

        userService.updateUserImgPath(image.getOriginalFilename(), savedFileName);

        return ResponseEntity.ok(BaseResponseDTO.of("이미지 업로드에 성공했습니다.", 200));

    }

}
