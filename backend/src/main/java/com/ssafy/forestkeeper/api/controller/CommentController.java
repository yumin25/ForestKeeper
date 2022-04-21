package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.comment.CommentRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.service.comment.CommentService;
import com.ssafy.forestkeeper.domain.repository.comment.CommentRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            @ApiResponse(code = 404, message = "댓글 작성에 필요한 정보를 찾을 수 없습니다."),
            @ApiResponse(code = 409, message = "댓글 작성에 실패했습니다.")
    })
    @PostMapping
    public ResponseEntity<? extends BaseResponseDTO> register(
            @ApiParam(value = "댓글 정보", required = true) @RequestBody @Valid CommentRegisterPostDTO commentRegisterPostDTO
    ) {

        try {
            commentService.registerComment(commentRegisterPostDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of("댓글 작성에 필요한 정보를 찾을 수 없습니다.", 404));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("댓글 작성에 실패했습니다.", 409));
        }

        return ResponseEntity.status(201).body(BaseResponseDTO.of("댓글 작성에 성공했습니다.", 201));

    }

}
