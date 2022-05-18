package com.ssafy.forestkeeper.application.service.mountain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRecommendResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainRecommendWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainSearchDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainSearchResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainVisitorRankResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainVisitorRankWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.mountain.MountainVisit;
import com.ssafy.forestkeeper.domain.dao.mountain.QMountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.plogging.QPlogging;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainVisitRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepositorySupport;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.MountainNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

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
    public MountainSearchResponseDTO searchMountain(String keyword, int page) {

        int batch = 8;

        page = Math.max(page, 1);

        List<Mountain> result = mountainRepositorySupport.findByNameContains(keyword, PageRequest.of((page - 1) * batch, batch));

        List<MountainSearchDTO> list = new ArrayList<>();

        result.forEach(mountain -> list.add(
                        MountainSearchDTO.builder()
                                .mountainCode(mountain.getCode())
                                .name(mountain.getName())
                                .address(mountain.getAddress())
                                .build()
                )
        );

        return MountainSearchResponseDTO.builder()
                .mountainSearchDTOList(list)
                .total(mountainRepositorySupport.findByNameContains(keyword).size())
                .build();

    }

    @Override
    public MountainRankWrapperResponseDTO getMountainRankByDistance(String mountainCode) {

        List<Tuple> ploggingList = ploggingRepositorySupport.rankByDistance(
                mountainRepository.findByCode(mountainCode)
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다.")));

        List<MountainRankResponseDTO> mountainRankResponseDTOList = new ArrayList<>();

        Image image;
        String imagePath;
        
        for(Tuple plogging : ploggingList) {
        	image = imageRepository.findByUser(plogging.get(qPlogging.user)).orElse(null);
        	
            if (image == null) imagePath = "";
            else imagePath = hosting + "thumb/" + image.getSavedFileName();
        	mountainRankResponseDTOList.add(
                    MountainRankResponseDTO.builder()
                            .nickname(plogging.get(qPlogging.user).getNickname())
                            .distance(plogging.get(qPlogging.distance.sum()))
                            .imagePath(imagePath)
                            .build()
            );
        }

        return MountainRankWrapperResponseDTO.builder()
                .mountainRankResponseDTOList(mountainRankResponseDTOList)
                .build();

    }

    @Override
    public MountainRankWrapperResponseDTO getMountainRankByCount(String mountainCode) {

        List<Tuple> ploggingList = ploggingRepositorySupport.rankByCount(
                mountainRepository.findByCode(mountainCode)
                        .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다."))
        );

        List<MountainRankResponseDTO> mountainRankResponseDTOList = new ArrayList<>();

        Image image;
        String imagePath;
        
        for(Tuple plogging : ploggingList) {
        	image = imageRepository.findByUser(plogging.get(qPlogging.user)).orElse(null);
        	
            if (image == null) imagePath = "";
            else imagePath = hosting + "thumb/" + image.getSavedFileName();
        	mountainRankResponseDTOList.add(
                    MountainRankResponseDTO.builder()
                            .nickname(plogging.get(qPlogging.user).getNickname())
                            .count(plogging.get(qPlogging.count()))
                            .imagePath(imagePath)
                            .build()
            );
        }

        return MountainRankWrapperResponseDTO.builder()
                .mountainRankResponseDTOList(mountainRankResponseDTOList)
                .build();

    }

    @Override
    public MountainRecommendWrapperResponseDTO getRecommendByDistance(double lat, double lng) {

        List<Tuple> recommendList = mountainRepositorySupport.findByDistance(lat, lng);

        List<MountainRecommendResponseDTO> nearMountainRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
                nearMountainRecommendResponseDTOList.add(
                        MountainRecommendResponseDTO.builder()
                                .mountainCode(near.get(qMountain).getCode())
                                .address(near.get(qMountain).getAddress())
                                .name(near.get(qMountain).getName())
                                .value(Math.round(Double.parseDouble(near.toArray()[0].toString()) * 10) / 10.0)
                                .build()
                )
        );

        return MountainRecommendWrapperResponseDTO.builder()
                .mountainRecommendResponseDTOList(nearMountainRecommendResponseDTOList)
                .build();

    }

    @Override
    public MountainRecommendWrapperResponseDTO getRecommendByHeight() {

        double avg = ploggingRepositorySupport.getAvg(
                userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                        .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다."))
        );

        List<Tuple> recommendList = mountainRepositorySupport.findByHeight(avg);

        List<MountainRecommendResponseDTO> nearMountainRecommendResponseDTOList = new ArrayList<>();

        recommendList.forEach(near ->
                nearMountainRecommendResponseDTOList.add(
                        MountainRecommendResponseDTO.builder()
                                .mountainCode(near.get(qMountain).getCode())
                                .address(near.get(qMountain).getAddress())
                                .name(near.get(qMountain).getName())
                                .value(near.get(qMountain).getHeight())
                                .build()
                )
        );

        return MountainRecommendWrapperResponseDTO.builder()
                .mountainRecommendResponseDTOList(nearMountainRecommendResponseDTOList)
                .build();

    }

    public MountainVisitorRankWrapperResponseDTO getVisitorRank() {

        List<MountainVisitorRankResponseDTO> list = new ArrayList<>();

        List<MountainVisit> visitList = mountainVisitRepository.findTop5ByOrderByVisitorCountDesc();

        for (MountainVisit visit : visitList) {
            Mountain mountain = visit.getMountain();

            list.add(
                    MountainVisitorRankResponseDTO.builder()
                            .mountainCode(mountain.getCode())
                            .address(mountain.getAddress()).mountainName(mountain.getName())
                            .visitorCount(visit.getVisitorCount())
                            .build());
        }

        return MountainVisitorRankWrapperResponseDTO.builder()
                .mountainVisitorRankResponseDTOList(list)
                .build();

    }

}
