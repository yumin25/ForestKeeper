package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.comment.CommunityModifyPatchDTO;
import com.ssafy.forestkeeper.application.dto.request.community.CommunityRegisterPostDTO;
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
            @ApiParam(value = "글 정보", required = true) @RequestBody @Valid CommunityRegisterPostDTO communityRegisterPostDTO
    ) {

        try {
            communityService.registerCommunity(communityRegisterPostDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 작성에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("글 작성에 성공했습니다.", 201));

    }

    @ApiOperation(value = "글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 목록 조회에 성공했습니다."),
            @ApiResponse(code = 409, message = "글 목록 조회에 실패했습니다.")
    })
    @GetMapping
    public ResponseEntity<? extends BaseResponseDTO> getList(
            @ApiParam(value = "커뮤니티 코드", required = true) @RequestParam @NotNull CommunityCode communityCode,
            @ApiParam(value = "페이지 번호") @RequestParam(defaultValue = "1") int page
    ) {

        CommunityGetListWrapperResponseDTO communityGetListWrapperResponseDTO = null;

        try {
            communityGetListWrapperResponseDTO = communityService.getCommunityList(communityCode, page);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 목록 조회에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(CommunityGetListWrapperResponseDTO.of("글 목록 조회에 성공했습니다.", 200, communityGetListWrapperResponseDTO));

    }

    @ApiOperation(value = "글 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 조회에 성공했습니다."),
            @ApiResponse(code = 404, message = "해당 글을 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "글 조회에 실패했습니다.")
    })
    @GetMapping("/{communityId}")
    public ResponseEntity<? extends BaseResponseDTO> get(
            @ApiParam(value = "커뮤니티 ID", required = true) @PathVariable @NotBlank String communityId
    ) {

        CommunityResponseDTO communityResponseDTO = null;

        try {
            communityResponseDTO = communityService.getCommunity(communityId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 조회에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(CommunityResponseDTO.of("글 조회에 성공했습니다.", 200, communityResponseDTO));

    }

    @ApiOperation(value = "글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 수정에 성공했습니다."),
            @ApiResponse(code = 404, message = "해당 글을 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "글 수정에 실패했습니다.")
    })
    @PatchMapping
    public ResponseEntity<? extends BaseResponseDTO> modify(
            @ApiParam(value = "글 정보", required = true) @RequestBody @Valid CommunityModifyPatchDTO communityModifyPatchDTO
    ) {

        try {
            communityService.modifyCommunity(communityModifyPatchDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("글 수정에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(BaseResponseDTO.of("글 수정에 성공했습니다.", 200));

    }

}
