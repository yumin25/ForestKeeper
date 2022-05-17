package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.community.CommunityModifyRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.community.CommunityResponseDTO;
import com.ssafy.forestkeeper.application.service.community.CommunityService;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "Community API", tags = {"Community"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    @ApiOperation(value = "글 등록")
    @ApiResponses({
            @ApiResponse(code = 201, message = "글 작성에 성공했습니다."),
            @ApiResponse(code = 400, message = "입력된 정보가 유효하지 않습니다."),
            @ApiResponse(code = 404, message = "글 작성에 필요한 정보를 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "글 작성에 실패했습니다."),
    })
    @PostMapping
    public ResponseEntity<? extends BaseResponseDTO> register(
            @ApiParam(value = "글 정보", required = true) @RequestBody @Valid CommunityRegisterRequestDTO communityRegisterRequestDTO
    ) {

        communityService.registerCommunity(communityRegisterRequestDTO);

        return ResponseEntity.status(201).body(BaseResponseDTO.of("글 작성 성공", 201));

    }

    @ApiOperation(value = "글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 목록 조회에 성공했습니다."),
            @ApiResponse(code = 404, message = "글 목록을 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "글 목록 조회에 실패했습니다.")
    })
    @GetMapping
    public ResponseEntity<? extends BaseResponseDTO> getList(
            @ApiParam(value = "산 ID", required = true) @RequestParam @NotBlank String mountainId,
            @ApiParam(value = "커뮤니티 코드", required = true) @RequestParam @NotNull CommunityCode communityCode,
            @ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page
    ) {

        return ResponseEntity.ok(CommunityGetListWrapperResponseDTO.of("글 목록 조회 성공", 200, communityService.getCommunityList(mountainId, communityCode, page)));

    }

    @ApiOperation(value = "글 검색")
    @GetMapping("/search")
    public ResponseEntity<? extends BaseResponseDTO> search(
            @ApiParam(value = "산 ID", required = true) @RequestParam @NotBlank String mountainId,
            @ApiParam(value = "커뮤니티 코드", required = true) @RequestParam @NotNull CommunityCode communityCode,
            @ApiParam(value = "검색 유형", required = true) @RequestParam @NotBlank String type,
            @ApiParam(value = "검색어", required = true) @RequestParam @NotBlank String keyword,
            @ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page
    ) {

        return ResponseEntity.ok(
                CommunityGetListWrapperResponseDTO.of("글 검색 성공", 200,
                        communityService.searchCommunity(mountainId, communityCode, type, keyword, page))
        );

    }

    @ApiOperation(value = "글 조회")
    @GetMapping("/{communityId}")
    public ResponseEntity<? extends BaseResponseDTO> get(
            @ApiParam(value = "글 ID", required = true) @PathVariable @NotBlank String communityId
    ) {

        return ResponseEntity.ok(CommunityResponseDTO.of("글 조회 성공", 200, communityService.getCommunity(communityId)));

    }

    @ApiOperation(value = "글 수정")
    @PatchMapping
    public ResponseEntity<? extends BaseResponseDTO> modify(
            @ApiParam(value = "글 정보", required = true) @RequestBody @Valid CommunityModifyRequestDTO communityModifyRequestDTO
    ) {

        communityService.modifyCommunity(communityModifyRequestDTO);

        return ResponseEntity.ok(BaseResponseDTO.of("글 수정 성공", 200));

    }

    @ApiOperation(value = "글 삭제")
    @DeleteMapping("/{communityId}")
    public ResponseEntity<? extends BaseResponseDTO> delete(
            @ApiParam(value = "글 ID", required = true) @PathVariable @NotBlank String communityId
    ) {

        communityService.deleteCommunity(communityId);

        return ResponseEntity.ok(BaseResponseDTO.of("글 삭제 성공", 200));

    }

}
