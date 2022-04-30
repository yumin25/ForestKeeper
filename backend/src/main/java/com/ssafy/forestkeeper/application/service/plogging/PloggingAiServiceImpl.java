package com.ssafy.forestkeeper.application.service.plogging;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PloggingAiServiceImpl implements PloggingAiService {
    private final PloggingRepository ploggingRepository;

    @Override
    public void detectObject(MultipartFile multipartFile, String ploggingId) throws IOException {
        int score = 0;
        List<AnnotateImageRequest> requests = new ArrayList<>();
        byte []byteArr = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ByteString imgBytes = ByteString.readFrom(inputStream);
        Image img = Image.newBuilder().setContent(imgBytes).build();

        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder()
                        .addFeatures(Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION))
                        .setImage(img)
                        .build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
                    int tmpScore = 0;
                    if(entity.getName().equals("Bottle")) tmpScore = 1000;
                    else if(entity.getName().equals("Packaged goods")) tmpScore = 800;
                    else tmpScore = 400;
                    tmpScore *= entity.getScore();
                    System.out.println(tmpScore);
                    score += tmpScore;

                    System.out.format("Object name: %s%n", entity.getName());
                    System.out.format("Confidence: %s%n", entity.getScore());
                    System.out.format("Score: %d%n", score);
                }
            }
        }

        Plogging plogging = ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
        plogging.setExp(score);
        ploggingRepository.save(plogging);
    }

    @Override
    public void detectLabels(MultipartFile multipartFile, String ploggingId) throws IOException {
        int score = 0;
        List<AnnotateImageRequest> requests = new ArrayList<>();
        byte []byteArr = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ByteString imgBytes = ByteString.readFrom(inputStream);
        Image img = Image.newBuilder().setContent(imgBytes).build();

        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    int tmpScore = 0;
                    if(annotation.getDescription().equals("Plastic Bag")) tmpScore = 600;
                    else if(annotation.getDescription().equals("Litter")) tmpScore = 600;
                    else if(annotation.getDescription().equals("Plastic")) tmpScore = 600;
                    else if(annotation.getDescription().equals("Packing Materials")) tmpScore = 600;
                    else if(annotation.getDescription().equals("Waste Container")) tmpScore = 500;
                    else if(annotation.getDescription().equals("Bottle")) tmpScore = 500;
                    else if(annotation.getDescription().equals("Tin Can")) tmpScore = 500;
                    else if(annotation.getDescription().equals("Box")) tmpScore = 500;
                    else if(annotation.getDescription().equals("Plastic bottle")) tmpScore = 400;
                    else if(annotation.getDescription().equals("Water bottle")) tmpScore = 400;
                    else if(annotation.getDescription().equals("Plastic Wrap")) tmpScore = 400;

                    tmpScore *= annotation.getScore();
                    System.out.println(tmpScore);
                    score += tmpScore;
                    System.out.format("%s : %f%n", annotation.getDescription(), annotation.getScore());
                }
            }
        }

        if(score<1000) score = 1000;
        System.out.format("Score : %d%n", score);

        Plogging plogging = ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
        plogging.setExp(score);
        ploggingRepository.save(plogging);
    }
}
