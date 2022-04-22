package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.comment.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(value = "Comment API", tags = {"Comment"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 등록")
    @ApiResponses({
            @ApiResponse(code = 201, message = "댓글 작성에 성공했습니다."),
            @ApiResponse(code = 400, message = "입력된 정보가 유효하지 않습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다. / 해당 글을 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "댓글 작성에 실패했습니다.")
    })
    @PostMapping
    public ResponseEntity<? extends BaseResponseDTO> register(
            @ApiParam(value = "댓글 정보", required = true) @RequestBody @Valid CommentRegisterPostDTO commentRegisterPostDTO
    ) {

        try {
            commentService.registerComment(commentRegisterPostDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("댓글 작성에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("댓글 작성에 성공했습니다.", 201));

    }

    @ApiOperation(value = "댓글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "댓글 목록 조회에 성공했습니다."),
            @ApiResponse(code = 404, message = "해당 글을 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "댓글 목록 조회에 실패했습니다.")
    })
    @GetMapping("/community/{communityId}")
    public ResponseEntity<? extends BaseResponseDTO> getList(
            @ApiParam(value = "글 ID", required = true) @PathVariable @NotBlank String communityId
    ) {

        CommentGetListWrapperResponseDTO commentGetListWrapperResponseDTO;

        try {
            commentGetListWrapperResponseDTO = commentService.getCommentList(communityId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("댓글 목록 조회에 실패했습니다.", 409));
        }

        return ResponseEntity.ok(CommentGetListWrapperResponseDTO.of("댓글 목록 조회에 성공했습니다.", 200, commentGetListWrapperResponseDTO));

    }

}
