package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apk")
public class ApkController {

    @GetMapping
    public ResponseEntity<?> download(HttpServletResponse response) throws IOException {

        try {
            ClassPathResource cpr = new ClassPathResource("apk/forest_keeper.apk");

            byte[] fileByte = FileCopyUtils.copyToByteArray(cpr.getInputStream());

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode("forest_keeper.apk", "UTF-8") + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            response.getOutputStream().write(fileByte);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseDTO.of("데이터가 존재하지 않습니다.", 404));
        }

        return ResponseEntity.ok(BaseResponseDTO.of("apk 다운로드에 성공했습니다.", 200));

    }

}
