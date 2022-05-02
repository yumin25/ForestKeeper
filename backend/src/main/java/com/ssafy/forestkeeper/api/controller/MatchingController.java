package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.matching.MatchingJoinPostDTO;
import com.ssafy.forestkeeper.application.dto.request.matching.MatchingRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.service.mathcing.MatchingService;
import com.ssafy.forestkeeper.application.service.mathcing.MatchingUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Matching API", tags = {"Mathcing"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchingController {

    private final MatchingService mathcingService;
    private final MatchingUserService matchingUserService;

    @ApiOperation(value = "매칭 글 등록")
    @ApiResponses({
        @ApiResponse(code = 201, message = "글 작성에 성공했습니다."),
        @ApiResponse(code = 400, message = "입력된 정보가 유효하지 않습니다."),
        @ApiResponse(code = 404, message = "글 작성에 필요한 정보를 찾을 수 없습니다."),
        @ApiResponse(code = 409, message = "글 작성에 실패했습니다."),
    })
    @PostMapping
    public ResponseEntity<? extends BaseResponseDTO> register(
        @ApiParam(value = "매칭 글 정보", required = true) @RequestBody @Valid MatchingRegisterPostDTO matchingRegisterPostDTO
    ) {

        if (matchingRegisterPostDTO.getTotal() == 0) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("총 인원은 1명 이상이어야 합니다.", 409));
        }

        try {
            mathcingService.registerMatching(matchingRegisterPostDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 작성에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("글 작성에 성공했습니다.", 201));

    }

    @ApiOperation(value = "매칭 합류")
    @ApiResponses({
        @ApiResponse(code = 200, message = "매칭 합류에 성공했습니다."),
        @ApiResponse(code = 400, message = "입력된 정보가 유효하지 않습니다."),
        @ApiResponse(code = 404, message = "필요한 정보를 찾을 수 없습니다."),
        @ApiResponse(code = 409, message = "매칭 합류에 실패했습니다."),
    })
    @PostMapping("/join")
    public ResponseEntity<? extends BaseResponseDTO> joinMatching(
        @ApiParam(value = "매칭 정보", required = true)@Valid @RequestBody MatchingJoinPostDTO matchingJoinPostDTO
    ) {
        try {
            matchingUserService.joinMatching(matchingJoinPostDTO.getMatchingId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(409).body(BaseResponseDTO.of("매칭에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("매칭 합류에 성공했습니다.", 200));

    }
}
