package com.ssafy.api.api.controller;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import com.ssafy.api.application.dto.response.user.UserInfoDTO;
import com.ssafy.api.application.service.s3.S3Service;
import com.ssafy.api.application.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "User API", tags = {"User"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    private final S3Service s3Service;


    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        String email = request.getHeader("Authorization-Id");
        UserInfoDTO userInfoDTO;
        try{
            userInfoDTO = userService.getUserDetail(email);
        } catch (IllegalStateException e){
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e){
            return ResponseEntity.status(409).body(BaseResponseDTO.of("사용자 정보 조회에 실패하였습니다.", 409));
        }
        return ResponseEntity.status(200).body(UserInfoDTO.of("사용자 정보 조회에 성공하였습니다.", 200, userInfoDTO));
    }

    @ApiOperation(value = "닉네임 변경")
    @GetMapping("/modify/nickname")
    public ResponseEntity<?> modifyNickname(@RequestParam("nickname") String nickname, HttpServletRequest request) {
        String email = request.getHeader("Authorization-Id");
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
        String email = request.getHeader("Authorization-Id");
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
        String email = request.getHeader("Authorization-Id");
        if(userService.withdraw(email)) return ResponseEntity.status(201).body(BaseResponseDTO.of("탈퇴하였습니다.", 201));
        return ResponseEntity.status(500).body(BaseResponseDTO.of("탈퇴에 실패하였습니다.", 500));
    }
    
	@PutMapping("/modify/profile")
	@ApiOperation(value = "회원 프로필 사진 수정")
	public Object updateUserImage(@RequestBody @ApiParam(value = "회원 프로필 사진.", required = true) MultipartFile image) {
		String savedFileName = s3Service.uploadFileToS3("user", image);
		userService.updateUserImgPath(image.getOriginalFilename(), savedFileName);
		return ResponseEntity.status(201).body(BaseResponseDTO.of("이미지 업로드에 성공했습니다.", 201));
	}
}
