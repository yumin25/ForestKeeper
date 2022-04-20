package com.ssafy.forestkeeper.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Plogging API", tags = {"Plogging"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @ApiOperation(value = "회원가입")
    @PostMapping
    public ResponseEntity<?> register(@RequestBody PloggingRegisterDTO ploggingRegisterDTO) {
        try {
            ploggingService.register(ploggingRegisterDTO);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("플로깅 등록에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("플로깅 등록에 성공했습니다.", 201));
    }
}
