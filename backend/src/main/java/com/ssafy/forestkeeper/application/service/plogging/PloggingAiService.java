package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingExperienceResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PloggingAiService {
    void detectObject(MultipartFile multipartFile, String ploggingId) throws IOException;
    PloggingExperienceResponseDTO detectLabels(MultipartFile multipartFile, String ploggingId) throws IOException;
    PloggingExperienceResponseDTO detectLabelsTest(MultipartFile multipartFile) throws IOException;
}
