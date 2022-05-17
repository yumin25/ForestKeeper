package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.*;
import com.ssafy.forestkeeper.application.service.mountain.MountainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Api(value = "Mountain API", tags = {"Mountain"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mountain")
public class MountainController {

    private final MountainService mountainService;

    @ApiOperation(value = "등산로")
    @GetMapping("/trail")
    public ResponseEntity<?> getMountainTrail(String mountainCode) {

        JSONObject trail;

        try {
            ClassPathResource cpr = new ClassPathResource("trail/" + mountainCode + ".json");

            byte[] bData = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            String jsonTxt = new String(bData, StandardCharsets.UTF_8);

            trail = (JSONObject) new JSONParser().parse(jsonTxt);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
        }

        return ResponseEntity.ok(MountainTrailResponseDTO.of("등산로 불러오기에 성공했습니다.", 200, trail));
    }

    @ApiOperation(value = "산 정보")
    @GetMapping("/{mountainCode}")
    public ResponseEntity<?> getMountainInfo(@PathVariable("mountainCode") String mountainCode) {

        return ResponseEntity.ok(
                MountainResponseDTO.of("산 정보 불러오기에 성공했습니다.", 200, mountainService.getMountainInfo(mountainCode))
        );

    }

    @ApiOperation(value = "산 검색")
    @GetMapping("")
    public ResponseEntity<?> searchMountain(@RequestParam String keyword,
                                            @RequestParam(defaultValue = "1") int page) {

        return ResponseEntity.ok(
                MountainSearchResponseDTO.of("산 검색에 성공했습니다.", 200, mountainService.searchMountain(keyword, page))
        );

    }

    @ApiOperation(value = "산 랭킹")
    @GetMapping("/rank/{mountainCode}")
    public ResponseEntity<?> getRank(@PathVariable("mountainCode") String mountainCode,
                                     @RequestParam String by) {

        MountainRankWrapperResponseDTO mountainRankWrapperResponseDTO = null;

        if ("distance".equals(by)) {
            mountainRankWrapperResponseDTO = mountainService.getMountainRankByDistance(mountainCode);
        } else if ("count".equals(by)) {
            mountainRankWrapperResponseDTO = mountainService.getMountainRankByCount(mountainCode);
        }

        return ResponseEntity.ok(
                MountainRankWrapperResponseDTO.of("산 랭킹 조회에 성공했습니다.", 200, mountainRankWrapperResponseDTO)
        );

    }

    @ApiOperation(value = "산 방문자 수 랭킹")
    @GetMapping("/rank")
    public ResponseEntity<?> getVisitorRank() {

        return ResponseEntity.ok(
                MountainVisitorRankWrapperResponseDTO.of("산 랭킹 조회에 성공했습니다.", 200, mountainService.getVisitorRank())
        );

    }

    @ApiOperation(value = "산 추천")
    @GetMapping("/recommend")
    public ResponseEntity<?> getRank(@RequestParam String by,
                                     @RequestParam(required = false) Double lat,
                                     @RequestParam(required = false) Double lng) {

        MountainRecommendWrapperResponseDTO mountainRecommendWrapperResponseDTO = null;

        if ("distance".equals(by)) {
            mountainRecommendWrapperResponseDTO = mountainService.getRecommendByDistance(lat, lng);
        } else if ("height".equals(by)) {
            mountainRecommendWrapperResponseDTO = mountainService.getRecommendByHeight();
        }

        return ResponseEntity.ok(
                MountainRecommendWrapperResponseDTO.of("산 랭킹 조회에 성공했습니다.", 200, mountainRecommendWrapperResponseDTO)
        );

    }

}
