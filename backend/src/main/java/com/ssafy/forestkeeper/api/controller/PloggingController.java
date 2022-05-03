package com.ssafy.forestkeeper.api.controller;

<<<<<<< backend/src/main/java/com/ssafy/forestkeeper/api/controller/PloggingController.java
=======
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingExperienceResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingAiService;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
>>>>>>> backend/src/main/java/com/ssafy/forestkeeper/api/controller/PloggingController.java
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;
import com.ssafy.forestkeeper.application.service.s3.S3Service;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Plogging API", tags = {"Plogging"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plogging")
public class PloggingController {

    private final PloggingService ploggingService;
    
    private final S3Service s3Service;

    private final PloggingAiService ploggingAiService;

    @ApiOperation(value = "플로깅 등록")
    @PostMapping
    public ResponseEntity<?> registerPlogging(@ModelAttribute PloggingRegisterDTO ploggingRegisterDTO) {
        try {
        	Plogging plogging = ploggingService.register(ploggingRegisterDTO);
        	if(ploggingRegisterDTO.getImage() != null) {
        		String savedFileName = s3Service.uploadFileToS3("plogging", ploggingRegisterDTO.getImage());
        		ploggingService.registerPloggingImg(ploggingRegisterDTO.getImage().getOriginalFilename(), savedFileName, plogging);
        	}
        	
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("플로깅 등록에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("플로깅 등록에 성공했습니다.", 201));
    }

    @ApiOperation(value = "플로깅 상세 조회")
    @GetMapping("/{ploggingId}")
    public ResponseEntity<?> getPloggingDetail(
        @ApiParam(value = "플로깅 ID", required = true) @PathVariable @NotBlank String ploggingId) {
        PloggingDetailResponseDTO ploggingDetailResponseDTO = null;
        try {
            ploggingDetailResponseDTO = ploggingService.get(ploggingId);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        }
        return ResponseEntity.ok(
            PloggingDetailResponseDTO.of("플로깅 조회에 성공했습니다.", 200, ploggingDetailResponseDTO));
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

    @ApiOperation(value = "쓰레기통 전체 목록")
    @GetMapping("/trash")
    public ResponseEntity<?> getTrashCanList() {

        try {

            List<TrashCan> list = ploggingService.getTrashCanList();

            TrashCanListWrapperResponseDTO trashCanListWrapperResponseDTO = TrashCanListWrapperResponseDTO.builder()
                .list(list).build();

            return ResponseEntity.ok(TrashCanListWrapperResponseDTO.of("전체 쓰레기통 조회에 성공했습니다.", 200,
                trashCanListWrapperResponseDTO));

        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        }
    }

    @ApiOperation(value = "특정 지역구 쓰레기통 목록")
    @GetMapping("/trash/{region}")
    public ResponseEntity<?> getTrashCanList(@PathVariable("region") String region) {

        try {

            Optional<List<TrashCan>> list = ploggingService.getTrashCanList(region);

            if (!list.isPresent() || list.get().size() == 0) {
                return ResponseEntity.status(404)
                    .body(BaseResponseDTO.of("쓰레기통 데이터가 존재하지 않습니다.", 404));
            }

            TrashCanListWrapperResponseDTO trashCanListWrapperResponseDTO = TrashCanListWrapperResponseDTO.builder()
                .list(list.get()).build();

            return ResponseEntity.ok(TrashCanListWrapperResponseDTO.of("지역구 쓰레기통 조회에 성공했습니다.", 200,
                trashCanListWrapperResponseDTO));

        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        }
    }

    @ApiOperation(value = "vision api로 분석 후 경험치 부여")
    @PostMapping("/ai")
    public ResponseEntity<?> detectObject(@RequestParam MultipartFile multipartFile, String ploggingId) {
        PloggingExperienceResponseDTO ploggingExperienceResponseDTO;
        try {
            ploggingExperienceResponseDTO = ploggingAiService.detectLabels(multipartFile, ploggingId);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("경험치 부여에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(PloggingExperienceResponseDTO.of("경험치 부여에 성공했습니다.", 201, ploggingExperienceResponseDTO));
    }
}
