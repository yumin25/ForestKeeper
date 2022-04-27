package com.ssafy.forestkeeper.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(value = "UserInfo API", tags = {"UserInfo"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userinfo")
public class UserInfoController {
	
	private final PloggingService ploggingService;

    @ApiOperation(value = "플로깅 목록 조회")
    @GetMapping
    public ResponseEntity<? extends BaseResponseDTO> getList(@ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page) {

        PloggingListWrapperResponseDTO ploggingListWrapperResponseDTO = null;

        try {
        	ploggingListWrapperResponseDTO = ploggingService.getPloggingList(page);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 목록 조회에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(PloggingListWrapperResponseDTO.of("글 목록 조회에 성공했습니다.", 200, ploggingListWrapperResponseDTO));

    }
}
