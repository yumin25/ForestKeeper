package com.ssafy.forestkeeper.api.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.forestkeeper.application.dto.request.plogging.Coordinates;
import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.MountainPloggingInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingExperienceResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.plogging.PloggingAiService;
import com.ssafy.forestkeeper.application.service.plogging.PloggingService;
import com.ssafy.forestkeeper.application.service.s3.S3Service;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<?> registerPlogging(@RequestPart(value = "dto", required = true) PloggingRegisterDTO ploggingRegisterDTO,
    		@RequestPart(value = "image", required = false) MultipartFile multipartFile) {
//        PloggingExperienceResponseDTO ploggingExperienceResponseDTO = null;
        
        try {
        	Plogging plogging = ploggingService.register(ploggingRegisterDTO);
//        	if(multipartFile != null) {
//                ploggingExperienceResponseDTO = ploggingAiService.detectLabels(multipartFile, plogging.getId());
//        		String savedFileName = s3Service.uploadFileToS3("plogging", multipartFile);
//        		ploggingService.registerPloggingImg(multipartFile.getOriginalFilename(), savedFileName, plogging);
//        	}
//            else {
//                ploggingExperienceResponseDTO = PloggingExperienceResponseDTO.builder().exp(0).build();
//            }
        	
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("플로깅 등록에 성공했습니다.", 201));
//        return ResponseEntity.status(201).body(PloggingExperienceResponseDTO.of("경험치 부여에 성공했습니다.", 201, ploggingExperienceResponseDTO));
    }

    @ApiOperation(value = "플로깅 상세 조회")
    @GetMapping("/{ploggingId}")
    public ResponseEntity<?> getPloggingDetail(
        @ApiParam(value = "플로깅 ID", required = true) @PathVariable @NotBlank String ploggingId) {
        PloggingDetailResponseDTO ploggingDetailResponseDTO = null;
        try {
            ploggingDetailResponseDTO = ploggingService.get(ploggingId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("경험치 부여에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(BaseResponseDTO.of("경험치 부여에 성공했습니다.", 201));
    }
    
    @ApiOperation(value = "산별 플로깅관련 정보 조회")
    @GetMapping
    public ResponseEntity<?> getMountainPlogging(@RequestParam String mountainCode) {
    	MountainPloggingInfoResponseDTO ploggingCumulativeResponseDTO;
        try {
        	ploggingCumulativeResponseDTO = ploggingService.getMountainPlogging(mountainCode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of(e.getMessage(), 409));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("산별 플로깅관련 정보 조회에 실패했습니다.", 409));
        }
        return ResponseEntity.status(200).body(MountainPloggingInfoResponseDTO.of("산별 플로깅관련 정보 조회에 성공했습니다.", 200, ploggingCumulativeResponseDTO));
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
    public ResponseEntity<?> detectObject(@RequestParam MultipartFile multipartFile) {
        PloggingExperienceResponseDTO ploggingExperienceResponseDTO;
        try {
            ploggingExperienceResponseDTO = ploggingAiService.detectLabelsTest(multipartFile);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(BaseResponseDTO.of("경험치 부여에 실패했습니다.", 409));
        }
        return ResponseEntity.status(201).body(PloggingExperienceResponseDTO.of("경험치 부여에 성공했습니다.", 201, ploggingExperienceResponseDTO));
    }
}
