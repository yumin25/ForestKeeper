package com.ssafy.api.application.service.mountain;

import com.querydsl.core.Tuple;
import com.ssafy.api.application.dto.response.mountain.MountainRankResponseDTO;
import com.ssafy.api.application.dto.response.mountain.MountainRankWrapperResponseDTO;
import com.ssafy.api.domain.dao.mountain.Mountain;
import com.ssafy.api.domain.dao.plogging.QPlogging;
import com.ssafy.api.domain.repository.mountain.MountainRepository;
import com.ssafy.api.domain.repository.mountain.MountainRepositorySupport;
import com.ssafy.api.domain.repository.plogging.PloggingRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService {

    private final MountainRepository mountainRepository;
    private final MountainRepositorySupport mountainRepositorySupport;
    private final PloggingRepositorySupport ploggingRepositorySupport;
    private QPlogging qPlogging = QPlogging.plogging;

    private int batch = 8;

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

        ploggingList.forEach(plogging ->
            mountainRankResponseDTOList.add(
                MountainRankResponseDTO.builder()
                    .nickname(plogging.get(qPlogging.user).getNickname())
                    .distance(plogging.get(qPlogging.distance.sum()))
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
}
