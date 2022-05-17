package com.ssafy.forestkeeper.application.service.mountain;

import com.querydsl.core.Tuple;
import com.ssafy.forestkeeper.application.dto.response.mountain.*;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.mountain.MountainVisit;
import com.ssafy.forestkeeper.domain.dao.mountain.QMountain;
import com.ssafy.forestkeeper.domain.dao.plogging.QPlogging;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainVisitRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.MountainNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService {

    private final MountainRepository mountainRepository;
    private final MountainVisitRepository mountainVisitRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final MountainRepositorySupport mountainRepositorySupport;
    private final PloggingRepositorySupport ploggingRepositorySupport;
    private QPlogging qPlogging = QPlogging.plogging;
    private QMountain qMountain = QMountain.mountain;

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    @Override
    public MountainResponseDTO getMountainInfo(String mountainCode) {

        return MountainResponseDTO.builder()
                .mountainInfo(mountainRepository.findByCode(mountainCode)
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다.")))
                .build();

    }

    @Override
    public Optional<List<Mountain>> searchMountain(String keyword, int page) {
        int batch = 8;
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
        Image image = imageRepository.findByUser(userRepository.findByEmailAndDelete(
                        SecurityContextHolder.getContext().getAuthentication().getName(), false).get())
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
    public MountainRecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng) {

        List<Tuple> recommendList = mountainRepositorySupport.findByDistance(lat, lng);

        List<MountainRecommendResponseDTO> nearMountainRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
                nearMountainRecommendResponseDTOList
                        .add(MountainRecommendResponseDTO.builder()
                                .mountainCode(near.get(qMountain).getCode())
                                .address(near.get(qMountain).getAddress())
                                .name(near.get(qMountain).getName())
                                .value(Math.round(Double.parseDouble(near.toArray()[0].toString()) * 10) / 10.0)
                                .build()));

        return MountainRecommendWrapperResponseDTO.builder()
                .mountainRecommendResponseDTOList(nearMountainRecommendResponseDTOList).build();
    }

    @Override
    public MountainRecommendWrapperResponseDTO getRecommendByHeight() {

        double avg = ploggingRepositorySupport.getAvg(userRepository.findByEmailAndDelete(
                SecurityContextHolder.getContext().getAuthentication().getName(), false).get());

        List<Tuple> recommendList = mountainRepositorySupport.findByHeight(avg);

        List<MountainRecommendResponseDTO> nearMountainRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
                nearMountainRecommendResponseDTOList
                        .add(MountainRecommendResponseDTO.builder()
                                .mountainCode(near.get(qMountain).getCode())
                                .address(near.get(qMountain).getAddress())
                                .name(near.get(qMountain).getName())
                                .value(near.get(qMountain).getHeight())
                                .build())
        );

        return MountainRecommendWrapperResponseDTO.builder()
                .mountainRecommendResponseDTOList(nearMountainRecommendResponseDTOList).build();
    }

    public MountainVisitorRankWrapperResponseDTO getVisiterRank() {
        List<MountainVisitorRankResponseDTO> list = new ArrayList<>();
        List<MountainVisit> visitList = mountainVisitRepository.findTop5ByOrderByVisiterCountDesc();
        for (MountainVisit visit : visitList) {
            Mountain mountain = visit.getMountain();
            list.add(MountainVisitorRankResponseDTO.builder()
                    .mountainCode(mountain.getCode())
                    .address(mountain.getAddress()).mountainName(mountain.getName())
                    .visitorCount(visit.getVisiterCount())
                    .build());
        }

        return MountainVisitorRankWrapperResponseDTO.builder()
                .list(list)
                .build();
    }
}
