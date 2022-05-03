package com.ssafy.forestkeeper.application.service.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.forestkeeper.application.service.user.UserService;
import com.ssafy.forestkeeper.util.image.FileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service {

	  private final AmazonS3Client amazonS3Client;
	  
	  @Value("${cloud.aws.s3.bucket}")
	  private String bucket;

	    public String uploadFileToS3(String category, MultipartFile multipartFile) {
	        String fileName = FileUtil.buildFileName(category, multipartFile.getOriginalFilename());
	        ObjectMetadata objectMetadata = new ObjectMetadata();
	        objectMetadata.setContentType(multipartFile.getContentType());
	        
	        try {
	            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
	            objectMetadata.setContentLength(bytes.length);
	            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

	            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayIs, objectMetadata));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println(amazonS3Client.getUrl(bucket, fileName).toString());
	        return fileName;
	    }
	    
}
