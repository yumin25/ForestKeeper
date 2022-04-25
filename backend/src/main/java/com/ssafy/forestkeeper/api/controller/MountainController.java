package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainSearch;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainSearchResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainTrailResponseDTO;
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

        return ResponseEntity.status(200).body(MountainTrailResponseDTO.of("등산로 불러오기에 성공했습니다.", 200, trail));
    }

    @ApiOperation(value = "산 정보")
    @GetMapping("/{mountainCode}")
    public ResponseEntity<?> getMountainInfo(@PathVariable("mountainCode") String mountainCode) {

        Optional<Mountain> mountainInfo = mountainService.getMountainInfo(mountainCode);

        if(!mountainInfo.isPresent()){
            return ResponseEntity.status(404).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
        }

        return ResponseEntity.status(200).body(
            MountainInfoResponseDTO.of("산 정보 불러오기에 성공했습니다.", 200, mountainInfo.get()));
    }

    @ApiOperation(value = "산 검색")
    @GetMapping("")
    public ResponseEntity<?> searchMountain(@RequestParam("keyword") String keyword) {

        try {
            System.out.println(keyword.getClass().getName());
            Optional<List<Mountain>> mountainList = mountainService.searchMountain(keyword);
//            System.out.println(mountainList);
            if(!mountainList.isPresent()){
                return ResponseEntity.status(404).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
            }

            return ResponseEntity.status(200).body(
                MountainSearchResponseDTO.of("산 검색에 성공했습니다.", 200, mountainList.get()));
        }catch (Exception e){
            System.err.println(e);
            return ResponseEntity.status(400).body(BaseResponseDTO.of("올바르지 않은 요청입니다.", 400));
        }
    }
}
