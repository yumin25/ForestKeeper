package com.ssafy.forestkeeper.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainNameListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;
import com.ssafy.forestkeeper.application.service.userinfo.UserInfoService;

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
	
	private final UserInfoService userInfoService;

	@ApiOperation(value = "플로깅 목록 조회")
    @GetMapping("/plogging")
    public ResponseEntity<? extends BaseResponseDTO> getPloggingList(@ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page) {

        PloggingListWrapperResponseDTO ploggingListWrapperResponseDTO = null;

        try {
        	ploggingListWrapperResponseDTO = userInfoService.getPloggingList(page);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 목록 조회에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(PloggingListWrapperResponseDTO.of("글 목록 조회에 성공했습니다.", 200, ploggingListWrapperResponseDTO));

    }
    
    @ApiOperation(value = "방문한 산 목록 조회")
    @GetMapping("/mountain")
    public ResponseEntity<? extends BaseResponseDTO> getMountainList(@ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page) {

    	 Optional<List<String>> mountainList = null;

        try {
        	mountainList = userInfoService.getMountainList(page);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("산 목록 조회에 실패했습니다.", 409));
        }
        return ResponseEntity.status(200).body(MountainNameListResponseDTO.of("산 목록 조회에 성공했습니다.", 200, MountainNameListResponseDTO.builder().list(mountainList.get()).build()));
    }
    
    @ApiOperation(value = "방문한 산 목록 조회")
    @GetMapping("/{mountainName}")
    public ResponseEntity<? extends BaseResponseDTO> getPloggingInMountain(@ApiParam(value = "산 이름", required = true) @PathVariable @NotBlank String mountainName) {

    	PloggingListWrapperResponseDTO ploggingListWrapperResponseDTO = null;
    	
        try {
        	ploggingListWrapperResponseDTO = userInfoService.getPloggingInMountain(mountainName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("플로깅 목록 조회에 실패했습니다.", 409));
        }
        return ResponseEntity.status(200).body(PloggingListWrapperResponseDTO.of("플로깅 목록 조회에 성공했습니다.", 200,ploggingListWrapperResponseDTO));
    }
    
}
