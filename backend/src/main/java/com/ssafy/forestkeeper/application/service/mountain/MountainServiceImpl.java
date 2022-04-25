package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService{

    private final MountainRepository mountainRepository;

    @Override
    public Optional<Mountain> getMountainInfo(String mountainCode) {
        Optional<Mountain> result = Optional.ofNullable(mountainRepository.findByCode(mountainCode));
        return result;
    }

    @Override
    public Optional<List<Mountain>> searchMountain(String keyword) {
        System.out.println("서비스 키워드 "+keyword);
        Optional<List<Mountain>> result = Optional.ofNullable(mountainRepository.findByNameContains("악"));
        return result;
    }
}
