package com.ssafy.forestkeeper.api.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(value = "Plogging API", tags = {"Plogging"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @ApiOperation(value = "플로깅 등록")
    @PostMapping
    public ResponseEntity<?> register(@RequestBody PloggingRegisterDTO ploggingRegisterDTO) {
        try {
            ploggingService.register(ploggingRegisterDTO);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("플로깅 등록에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("플로깅 등록에 성공했습니다.", 201));
    }
    
    @ApiOperation(value = "플로깅 상세 조회")
    @GetMapping("/{ploggingId}")
    public ResponseEntity<?> get(@ApiParam(value = "플로깅 ID", required = true) @PathVariable @NotBlank String ploggingId) {
    	PloggingDetailResponseDTO DTO = null;
        try {
            DTO = ploggingService.get(ploggingId);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        }
        return ResponseEntity.ok(PloggingDetailResponseDTO.of("플로깅 조회에 성공했습니다.", 200, DTO));
    }
    
    @ApiOperation(value = "경험치 부여")
    @PatchMapping
    public ResponseEntity<?> registerExp(@RequestBody ExpRegisterDTO expRegisterDTO) {
        try {
            ploggingService.registerExp(expRegisterDTO);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("경험치 부여에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("경험치 부여에 성공했습니다.", 201));
    }
}
