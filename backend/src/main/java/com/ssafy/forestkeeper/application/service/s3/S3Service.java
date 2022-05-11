package com.ssafy.forestkeeper.application.service.s3;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.forestkeeper.util.image.FileUtil;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class S3Service {

	  private final AmazonS3Client amazonS3Client;
	  
	  @Value("${cloud.aws.s3.bucket}")
	  private String bucket;

	    public String uploadFileToS3(String category, MultipartFile multipartFile) {
	        String fileName = FileUtil.buildFileName(multipartFile.getOriginalFilename());
	        ObjectMetadata objectMetadata = new ObjectMetadata();
	        objectMetadata.setContentType(multipartFile.getContentType());
	        
	        try {
	            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
	            objectMetadata.setContentLength(bytes.length);
	            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

	            amazonS3Client.putObject(new PutObjectRequest(bucket, category + "/" + fileName, byteArrayIs, objectMetadata));
	            if(category.equals("user")) {
	            	uploadThumbFile(multipartFile, fileName);
	            }
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println(amazonS3Client.getUrl(bucket, fileName).toString());
	        return fileName;
	    }
	    
	    // upload thumbnail image file
	    public void uploadThumbFile(MultipartFile image, String fileName) {
	      try {
	        // make thumbnail image for s3
	        BufferedImage bufferImage = ImageIO.read(image.getInputStream());
	        BufferedImage thumbnailImage = Thumbnails.of(bufferImage).size(100, 100).asBufferedImage();

	        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
	        String imageType = image.getContentType();
	        ImageIO.write(thumbnailImage, imageType.substring(imageType.indexOf("/")+1), thumbOutput);

	        // set metadata
	        ObjectMetadata thumbObjectMetadata = new ObjectMetadata();
	        byte[] thumbBytes = thumbOutput.toByteArray();
	        thumbObjectMetadata.setContentLength(thumbBytes.length);
	        thumbObjectMetadata.setContentType(image.getContentType());

	        // save in s3
	        InputStream thumbInput = new ByteArrayInputStream(thumbBytes);
	        amazonS3Client.putObject(new PutObjectRequest(bucket, "thumb/"+ fileName, thumbInput, thumbObjectMetadata));
		
	        thumbInput.close();
	        thumbOutput.close();
	    }catch(Exception e){
            e.printStackTrace();
	    }
	  }
	    
}
