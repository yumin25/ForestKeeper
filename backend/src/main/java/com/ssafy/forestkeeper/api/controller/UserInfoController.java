package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserPloggingInfoDTO;
import com.ssafy.forestkeeper.application.service.userinfo.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(value = "UserInfo API", tags = {"UserInfo"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userinfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @ApiOperation(value = "플로깅 목록 조회")
    @GetMapping("/plogging")
    public ResponseEntity<? extends BaseResponseDTO> getPloggingList(
            @ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page) {

        return ResponseEntity.ok(
                PloggingGetListWrapperResponseDTO.of("글 목록 조회에 성공했습니다.", 200, userInfoService.getPloggingList(page))
        );

    }

    @ApiOperation(value = "방문한 산 목록 조회")
    @GetMapping("/mountain")
    public ResponseEntity<? extends BaseResponseDTO> getMountainList(
            @ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page) {

        return ResponseEntity.ok(
                MountainUserInfoWrapperResponseDTO.of("산 목록 조회에 성공했습니다.", 200, userInfoService.getMountainList(page))
        );

    }

    @ApiOperation(value = "산별 플로깅 목록 조회")
    @GetMapping("/{mountainCode}")
    public ResponseEntity<? extends BaseResponseDTO> getPloggingInMountain(
            @ApiParam(value = "산 코드", required = true) @PathVariable @NotBlank String mountainCode) {

        return ResponseEntity.ok(
                PloggingGetListWrapperResponseDTO.of("플로깅 목록 조회에 성공했습니다.", 200, userInfoService.getPloggingInMountain(mountainCode))
        );

    }

    @ApiOperation(value = "유저 누적 플로깅 정보")
    @GetMapping
    public ResponseEntity<? extends BaseResponseDTO> getUserAccumulative() {

        return ResponseEntity.ok(
                UserPloggingInfoDTO.of("유저 누적 플로깅 정보 조회에 성공했습니다.", 200, userInfoService.getUserAccumulative())
        );

    }

}
