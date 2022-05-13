package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainSearchResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainTrailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.RecommendWrapperResponseDTO;
import com.ssafy.forestkeeper.application.service.mountain.MountainService;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            String jsonTxt = new String(bdata, StandardCharsets.UTF_8);

            trail = (JSONObject) new JSONParser().parse(jsonTxt);

        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(404).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
        }

        return ResponseEntity.status(200)
            .body(MountainTrailResponseDTO.of("등산로 불러오기에 성공했습니다.", 200, trail));
    }

    @ApiOperation(value = "산 정보")
    @GetMapping("/{mountainCode}")
    public ResponseEntity<?> getMountainInfo(@PathVariable("mountainCode") String mountainCode) {

        Optional<Mountain> mountainInfo = mountainService.getMountainInfo(mountainCode);

        if (!mountainInfo.isPresent()) {
            return ResponseEntity.status(404).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
        }

        return ResponseEntity.status(200).body(
            MountainInfoResponseDTO.of("산 정보 불러오기에 성공했습니다.", 200, mountainInfo.get()));
    }

    @ApiOperation(value = "산 검색")
    @GetMapping("")
    public ResponseEntity<?> searchMountain(@RequestParam("keyword") String keyword,
        @RequestParam(required = false, value = "page") Integer page) {

        try {
            if (page == null || page < 1) {
                page = 1;
            }

            page -= 1;

            Optional<List<Mountain>> mountainList = mountainService.searchMountain(keyword, page);
            int total = mountainService.totalSearch(keyword);

            if (!mountainList.isPresent() || mountainList.get().size() == 0) {
                return ResponseEntity.status(404).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
            }

            return ResponseEntity.status(200).body(
                MountainSearchResponseDTO.of("산 검색에 성공했습니다.", 200, mountainList.get(), total));
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(400).body(BaseResponseDTO.of("올바르지 않은 요청입니다.", 400));
        }
    }

    @ApiOperation(value = "산 랭킹 검색")
    @GetMapping("/rank/{mountainCode}")
    public ResponseEntity<?> getRank(@PathVariable("mountainCode") String mountainCode,
        @RequestParam("by") String by) {

        try {
            MountainRankWrapperResponseDTO mountainRankWrapperResponseDTO = null;

            if ("distance".equals(by)) {
                mountainRankWrapperResponseDTO = mountainService.getMountainRankByDistance(
                    mountainCode);
            } else if ("count".equals(by)) {
                mountainRankWrapperResponseDTO = mountainService.getMountainRankByCount(
                    mountainCode);
            }

            return ResponseEntity.status(200).body(
                MountainRankWrapperResponseDTO.of("산 랭킹 조회에 성공했습니다.", 200,
                    mountainRankWrapperResponseDTO));
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(400).body(BaseResponseDTO.of("올바르지 않은 요청입니다.", 400));
        }
    }

    @ApiOperation(value = "산 랭킹 검색")
    @GetMapping("/recommend")
    public ResponseEntity<?> getRank(@RequestParam("by") String by,
        @RequestParam(name = "lat", required = false) Double lat,
        @RequestParam(name = "lng", required = false) Double lng) {

        try {
            RecommendWrapperResponseDTO recommendWrapperResponseDTO = null;

            if ("distance".equals(by)) {
                recommendWrapperResponseDTO = mountainService.getRecommendByDistance(lat, lng);
            } else if ("height".equals(by)) {
                recommendWrapperResponseDTO = mountainService.getRecommendByHeight();
            }

            return ResponseEntity.status(200).body(
                RecommendWrapperResponseDTO.of("산 랭킹 조회에 성공했습니다.", 200,
                    recommendWrapperResponseDTO));
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(400).body(BaseResponseDTO.of("올바르지 않은 요청입니다.", 400));
        }
    }
}
