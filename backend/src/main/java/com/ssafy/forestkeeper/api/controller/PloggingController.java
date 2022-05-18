package com.ssafy.forestkeeper.api.controller;


import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.*;
import com.ssafy.forestkeeper.application.service.plogging.PloggingAiService;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;
import com.ssafy.forestkeeper.application.service.s3.AwsS3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Api(value = "Plogging API", tags = {"Plogging"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    private final AwsS3Service awsS3Service;

    private final PloggingAiService ploggingAiService;

    @ApiOperation(value = "플로깅 등록")
    @PostMapping
    public ResponseEntity<?> registerPlogging(@RequestPart(value = "dto", required = true) PloggingRegisterRequestDTO ploggingRegisterRequestDTO,
                                              @RequestPart(value = "image", required = false) MultipartFile multipartFile) {

        PloggingRegisterResponseDTO ploggingRegisterResponseDTO = PloggingRegisterResponseDTO.builder()
                .ploggingId(ploggingService.register(ploggingRegisterRequestDTO).getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(PloggingRegisterResponseDTO.of("플로깅 등록에 성공했습니다.", 201, ploggingRegisterResponseDTO));

    }

    @ApiOperation(value = "플로깅 상세 조회")
    @GetMapping("/{ploggingId}")
    public ResponseEntity<?> getPloggingDetail(
            @ApiParam(value = "플로깅 ID", required = true) @PathVariable @NotBlank String ploggingId) {

        return ResponseEntity.ok(
                PloggingDetailResponseDTO.of("플로깅 조회에 성공했습니다.", 200, ploggingService.get(ploggingId))
        );

    }

    @ApiOperation(value = "경험치 부여")
    @PatchMapping
    public ResponseEntity<?> registerExp(@RequestBody ExpRegisterRequestDTO expRegisterRequestDTO) {

        ploggingService.registerExp(expRegisterRequestDTO);

        return ResponseEntity.ok(BaseResponseDTO.of("경험치 부여에 성공했습니다.", 200));

    }

    @ApiOperation(value = "산별 플로깅관련 정보 조회")
    @GetMapping
    public ResponseEntity<?> getMountainPlogging(@RequestParam String mountainCode) {

        return ResponseEntity.ok(
                MountainPloggingInfoResponseDTO.of("산별 플로깅 관련 정보 조회에 성공했습니다.", 200, ploggingService.getMountainPlogging(mountainCode))
        );

    }

    @ApiOperation(value = "쓰레기통 전체 목록")
    @GetMapping("/trash")
    public ResponseEntity<?> getTrashCanList() {

        return ResponseEntity.ok(
                TrashCanListWrapperResponseDTO.of("전체 쓰레기통 조회에 성공했습니다.", 200, ploggingService.getTrashCanList())
        );

    }

    @ApiOperation(value = "특정 지역구 쓰레기통 목록")
    @GetMapping("/trash/{region}")
    public ResponseEntity<?> getTrashCanList(@PathVariable("region") String region) {

        return ResponseEntity.ok(
                TrashCanListWrapperResponseDTO.of("지역구 쓰레기통 조회에 성공했습니다.", 200, ploggingService.getTrashCanList(region))
        );

    }

    @ApiOperation(value = "vision api로 분석 후 경험치 부여")
    @PostMapping("/ai")
    public ResponseEntity<?> detectObject(@RequestParam MultipartFile image, @RequestParam String ploggingId) throws IOException {

        PloggingExperienceResponseDTO ploggingExperienceResponseDTO = ploggingAiService.detectLabels(image, ploggingId);

        String savedFileName = awsS3Service.uploadFileToS3("plogging", image);

        ploggingService.registerPloggingImg(image.getOriginalFilename(), savedFileName, ploggingId);

        return ResponseEntity.ok(
                PloggingExperienceResponseDTO.of("경험치 부여에 성공했습니다.", 200, ploggingExperienceResponseDTO)
        );

    }

    @ApiOperation(value = "웰컴 페이지에 보여주는 정보")
    @GetMapping("/welcome")
    public ResponseEntity<?> getTotalInfo() {

        return ResponseEntity.ok(
                PloggingTotalInfoResponseDTO.of("총 참여인원, 총 거리 조회에 성공했습니다.", 200, ploggingService.getTotalInfo())
        );

    }

}
