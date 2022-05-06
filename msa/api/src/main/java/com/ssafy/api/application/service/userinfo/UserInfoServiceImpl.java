package com.ssafy.api.application.service.userinfo;

import com.ssafy.api.application.dto.response.plogging.PloggingListResponseDTO;
import com.ssafy.api.application.dto.response.plogging.PloggingListWrapperResponseDTO;
import com.ssafy.api.domain.dao.mountain.Mountain;
import com.ssafy.api.domain.dao.plogging.Plogging;
import com.ssafy.api.domain.repository.image.ImageRepository;
import com.ssafy.api.domain.repository.mountain.MountainRepository;
import com.ssafy.api.domain.repository.plogging.PloggingRepository;
import com.ssafy.api.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final PloggingRepository ploggingRepository;

    private final UserRepository userRepository;

	private final MountainRepository mountainRepository;
	
	private final ImageRepository imageRepository;
	
    @Value("${cloud.aws.s3.hosting}")
    public String hosting;
    
	@Override
	public PloggingListWrapperResponseDTO getPloggingList(int page, String email) {
        List<Plogging> ploggingList = ploggingRepository.findByUserId(userRepository.findByEmailAndDelete(email,false).get().getId(),PageRequest.of(page - 1, 10))
                .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

    	List<PloggingListResponseDTO> ploggingListResponseDTOList = new ArrayList<>();
		
    	ploggingList.forEach(plogging ->
    		ploggingListResponseDTOList.add(
                        PloggingListResponseDTO.builder()
                        		.date(plogging.getStartTime().toLocalDate().toString())
                        		.ploggingId(plogging.getId())
                        		.distance(plogging.getDistance())
                        		.time(plogging.getDurationTime())
                        		.exp(plogging.getExp())
                        		.mountainName(plogging.getMountain().getName())
                        		.imagePath(hosting + imageRepository.findByPloggingId(plogging.getId()).get().getSavedFileName())
                                .build()
                )
        );

        return PloggingListWrapperResponseDTO.builder()
                .list(ploggingListResponseDTOList)
                .build();
	}

	@Override
	public Optional<List<String>> getMountainList(int page, String email) {
		Set<String> set = new HashSet<>();
        List<Plogging> ploggingList = ploggingRepository.findByUserId(userRepository.findByEmailAndDelete(email,false).get().getId())
                .orElseThrow(() -> new IllegalArgumentException("플로깅 기록을 찾을 수 없습니다."));
        ploggingList.forEach(plogging ->
        	set.add(plogging.getMountain().getName())
        );
        List<String> list = new ArrayList<>();
        set.forEach(mntn ->
        	list.add(mntn)
        );
        Optional<List<String>> MountainNameList = Optional.ofNullable(list);
        
	    return MountainNameList;
	}
	
	@Override
	public PloggingListWrapperResponseDTO getPloggingInMountain(String mountainName, String email) {
		Mountain mountain = mountainRepository.findByName(mountainName)
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
        List<Plogging> ploggingList = ploggingRepository.findByUserIdAndMountainId(userRepository.findByEmailAndDelete(email,false).get().getId(),mountain.getId())
                .orElseThrow(() -> new IllegalArgumentException("플로깅 기록을 찾을 수 없습니다."));

    	List<PloggingListResponseDTO> ploggingListResponseDTOList = new ArrayList<>();

    	ploggingList.forEach(plogging ->
    		ploggingListResponseDTOList.add(
                        PloggingListResponseDTO.builder()
                        		.date(plogging.getStartTime().toLocalDate().toString())
                        		.ploggingId(plogging.getId())
                        		.distance(plogging.getDistance())
                        		.time(plogging.getDurationTime())
                        		.exp(plogging.getExp())
                        		.mountainName(plogging.getMountain().getName())
                        		.imagePath(hosting + imageRepository.findByPloggingId(plogging.getId()).get().getSavedFileName())
                                .build()
                )
        );

        return PloggingListWrapperResponseDTO.builder()
                .list(ploggingListResponseDTOList)
                .build();
	}
}
