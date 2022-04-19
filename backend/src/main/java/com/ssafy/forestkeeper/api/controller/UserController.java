package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.user.UserRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User API", tags = {"User"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입에 성공했습니다."),
            @ApiResponse(code = 409, message = "회원가입에 실패했습니다. / 해당 이메일로 가입된 계정이 이미 존재합니다.")
    })
    @PostMapping
    public ResponseEntity<?> register(UserRegisterPostDTO userRegisterPostDTO) {

        try {
            Integer statusCode = userService.registerUser(userRegisterPostDTO);

            if (statusCode == 409) {
                return ResponseEntity.status(409).body(BaseResponseDTO.of("해당 이메일로 가입된 계정이 이미 존재합니다.", 409));
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("회원가입에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("회원가입에 성공했습니다.", 201));

    }


}
