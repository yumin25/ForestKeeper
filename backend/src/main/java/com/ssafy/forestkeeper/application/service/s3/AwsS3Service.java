package com.ssafy.forestkeeper.application.service.s3;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    String uploadFileToS3(String category, MultipartFile multipartFile);

    void uploadThumbFile(MultipartFile image, String fileName, int size);

}
