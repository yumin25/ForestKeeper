package com.ssafy.forestkeeper.application.service.plogging;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PloggingAiService {
    void detectObject(MultipartFile multipartFile, String ploggingId) throws IOException;
    void detectLabels(MultipartFile multipartFile, String ploggingId) throws IOException;
}
