package com.ssafy.forestkeeper.application.service.plogging;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingExperienceResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    public PloggingExperienceResponseDTO detectLabels(MultipartFile multipartFile, String ploggingId) throws IOException {
        int exp = 0;
        HashMap<String, Integer> experience = new HashMap<>();
        experience.put("Plastic bag", 600);
        experience.put("Litter", 600);
        experience.put("Plastic", 600);
        experience.put("Packing materials", 600);
        experience.put("Waste container", 500);
        experience.put("Waste", 500);
        experience.put("Bottle", 500);
        experience.put("Tin can", 500);
        experience.put("Box", 500);
        experience.put("Glass bottle", 500);
        experience.put("Beverage can", 400);
        experience.put("Aluminum can", 400);
        experience.put("Plastic bottle", 400);
        experience.put("Water bottle", 400);
        experience.put("Plastic wrap", 400);

        HashMap<String, String> translate = new HashMap<>();
        translate.put("Plastic bag", "비닐봉지");
        translate.put("Litter", "쓰레기");
        translate.put("Plastic", "플라스틱");
        translate.put("Packing materials", "포장재");
        translate.put("Waste container", "폐기물 용기");
        translate.put("Waste", "폐기물");
        translate.put("Bottle", "병");
        translate.put("Tin can", "깡통");
        translate.put("Beverage can", "음료수 캔");
        translate.put("Aluminum can", "알루미늄 캔");
        translate.put("Box", "박스");
        translate.put("Glass bottle", "유리병");
        translate.put("Plastic bottle", "플라스틱 병");
        translate.put("Water bottle", "물병");
        translate.put("Plastic wrap", "비닐랩");

        List<AnnotateImageRequest> requests = new ArrayList<>();
        byte []byteArr = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ByteString imgBytes = ByteString.readFrom(inputStream);
        Image img = Image.newBuilder().setContent(imgBytes).build();

        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);
        List<String> type = new ArrayList<>();
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return PloggingExperienceResponseDTO.builder().exp(0).build();
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if(!experience.containsKey(annotation.getDescription())) continue;
                    type.add(translate.get(annotation.getDescription()));
                    int tmpScore = experience.get(annotation.getDescription());
                    tmpScore *= annotation.getScore();
                    System.out.println(tmpScore);
                    exp += tmpScore;
                    System.out.format("성공 %s : %f%n", annotation.getDescription(), annotation.getScore());
                }
            }
        }

        if(exp<1000) exp = 1000;
        System.out.format("Score : %d%n", exp);

        Plogging plogging = ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
        plogging.setExp(exp);
        ploggingRepository.save(plogging);
        return PloggingExperienceResponseDTO.builder().exp(exp).build();
    }

    @Override
    public PloggingExperienceResponseDTO detectLabelsTest(MultipartFile multipartFile) throws IOException {
        int exp = 0;
        HashMap<String, Integer> experience = new HashMap<>();
        experience.put("Plastic bag", 600);
        experience.put("Litter", 600);
        experience.put("Plastic", 600);
        experience.put("Packing materials", 600);
        experience.put("Waste container", 500);
        experience.put("Waste", 500);
        experience.put("Bottle", 500);
        experience.put("Tin can", 500);
        experience.put("Box", 500);
        experience.put("Glass bottle", 500);
        experience.put("Beverage can", 400);
        experience.put("Aluminum can", 400);
        experience.put("Plastic bottle", 400);
        experience.put("Water bottle", 400);
        experience.put("Plastic wrap", 400);

        HashMap<String, String> translate = new HashMap<>();
        translate.put("Plastic bag", "비닐봉지");
        translate.put("Litter", "쓰레기");
        translate.put("Plastic", "플라스틱");
        translate.put("Packing materials", "포장재");
        translate.put("Waste container", "폐기물 용기");
        translate.put("Waste", "폐기물");
        translate.put("Bottle", "병");
        translate.put("Tin can", "깡통");
        translate.put("Beverage can", "음료수 캔");
        translate.put("Aluminum can", "알루미늄 캔");
        translate.put("Box", "박스");
        translate.put("Glass bottle", "유리병");
        translate.put("Plastic bottle", "플라스틱 병");
        translate.put("Water bottle", "물병");
        translate.put("Plastic wrap", "비닐랩");

        List<AnnotateImageRequest> requests = new ArrayList<>();
        byte []byteArr = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ByteString imgBytes = ByteString.readFrom(inputStream);
        Image img = Image.newBuilder().setContent(imgBytes).build();

        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);
        List<String> type = new ArrayList<>();
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return PloggingExperienceResponseDTO.builder().exp(0).build();
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if(!experience.containsKey(annotation.getDescription())) continue;
                    type.add(translate.get(annotation.getDescription()));
                    int tmpScore = experience.get(annotation.getDescription());
                    tmpScore *= annotation.getScore();
                    System.out.println(tmpScore);
                    exp += tmpScore;
                    System.out.format("성공 %s : %f%n", annotation.getDescription(), annotation.getScore());
                }
            }
        }

        if(exp<1000) exp = 1000;
        System.out.format("Score : %d%n", exp);

        return PloggingExperienceResponseDTO.builder().exp(exp).type(type).build();
    }
}
