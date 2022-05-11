package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.application.dto.response.mountain.RecommendResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.RecommendWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.QMountain;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.QPlogging;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService {

    private final MountainRepository mountainRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final MountainRepositorySupport mountainRepositorySupport;
    private final PloggingRepositorySupport ploggingRepositorySupport;
    private QPlogging qPlogging = QPlogging.plogging;
    private QMountain qMountain = QMountain.mountain;

    private int batch = 8;

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    @Override
    public Optional<Mountain> getMountainInfo(String mountainCode) {
        return mountainRepository.findByCode(mountainCode);

    }

    @Override
    public Optional<List<Mountain>> searchMountain(String keyword, int page) {
        Optional<List<Mountain>> result = Optional.ofNullable(
            mountainRepositorySupport.findByNameContains(keyword,
                PageRequest.of(page * batch, batch)));
        return result;
    }

    @Override

    public MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode) {

        List<Tuple> ploggingList = ploggingRepositorySupport.rankByDistance(
            mountainRepository.findByCode(mountainCode).get());

        List<MountainRankResponseDTO> mountainRankResponseDTOList = new ArrayList<>();
        Image image = imageRepository.findByUserId(userRepository.findByEmailAndDelete(
                SecurityContextHolder.getContext().getAuthentication().getName(), false).get().getId())
            .orElse(null);
        String imagePath;
        if (image == null) {
            imagePath = "";
        } else {
            imagePath = hosting + image.getSavedFileName();
        }

        ploggingList.forEach(plogging ->
            mountainRankResponseDTOList.add(
                MountainRankResponseDTO.builder()
                    .nickname(plogging.get(qPlogging.user).getNickname())
                    .distance(plogging.get(qPlogging.distance.sum()))
                    .imagePath(imagePath)
                    .build()
            )
        );

        return MountainRankWrapperResponseDTO.builder()
            .mountainRankResponseDTOList(mountainRankResponseDTOList)
            .build();
    }

    @Override
    public MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode) {

        List<Tuple> ploggingList = ploggingRepositorySupport.rankByCount(
            mountainRepository.findByCode(mountainCode).get());

        List<MountainRankResponseDTO> mountainRankResponseDTOList = new ArrayList<>();

        ploggingList.forEach(plogging ->
            mountainRankResponseDTOList.add(
                MountainRankResponseDTO.builder()
                    .nickname(plogging.get(qPlogging.user).getNickname())
                    .count(plogging.get(qPlogging.count()))
                    .build()
            )
        );

        return MountainRankWrapperResponseDTO.builder()
            .mountainRankResponseDTOList(mountainRankResponseDTOList)
            .build();

    }

    public int totalSearch(String keyword) {

        return mountainRepositorySupport.findByNameContains(keyword).size();
    }

    @Override
    public RecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng) {

        List<Tuple> recommendList = mountainRepositorySupport.findByDistance(lat, lng);

        List<RecommendResponseDTO> nearRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
            nearRecommendResponseDTOList
                .add(RecommendResponseDTO.builder()
                    .mountainCode(near.get(qMountain).getCode())
                    .address(near.get(qMountain).getAddress())
                    .name(near.get(qMountain).getName())
                    .value(Math.round(Double.parseDouble(near.toArray()[0].toString())*10)/10.0)
                    .build()));

        return RecommendWrapperResponseDTO.builder()
            .recommendResponseDTOList(nearRecommendResponseDTOList).build();
    }

    @Override
    public RecommendWrapperResponseDTO getRecommendByHeight() {

        double avg = ploggingRepositorySupport.getAvg(userRepository.findByEmailAndDelete(
            SecurityContextHolder.getContext().getAuthentication().getName(), false).get());

        List<Tuple> recommendList = mountainRepositorySupport.findByHeight(avg);

        List<RecommendResponseDTO> nearRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
            nearRecommendResponseDTOList
                .add(RecommendResponseDTO.builder()
                    .mountainCode(near.get(qMountain).getCode())
                    .address(near.get(qMountain).getAddress())
                    .name(near.get(qMountain).getName())
                    .value(near.get(qMountain).getHeight())
                    .build())
        );

        return RecommendWrapperResponseDTO.builder()
            .recommendResponseDTOList(nearRecommendResponseDTOList).build();
    }
}
