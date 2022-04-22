package com.ssafy.forestkeeper.application.service.plogging;

import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PloggingServiceImpl implements PloggingService{
	
    private final PloggingRepository ploggingRepository;
	
    private final UserRepository userRepository;
	
	private final MountainRepository mountainRepository;

	@Override
	public void register(PloggingRegisterDTO ploggingRegisterDTO) {
		Plogging plogging = Plogging.builder()
								.distance(ploggingRegisterDTO.getDistance())
								.startTime(ploggingRegisterDTO.getStartTime())
								.endTime(ploggingRegisterDTO.getEndTime())
								.exp(0L)
								.user(userRepository.getById(ploggingRegisterDTO.getUserId()))
								.mountain(mountainRepository.getById(ploggingRegisterDTO.getMountainId()))
								.build();
		ploggingRepository.save(plogging);
	}

	@Override
	public PloggingDetailResponseDTO get(String ploggingId) {
		Plogging plogging = ploggingRepository.getById(ploggingId);
		return PloggingDetailResponseDTO.builder()
				.date(plogging.getStartTime().toLocalDate().toString())
				.mountainName(plogging.getMountain().getName())
				.distance(plogging.getDistance())
				.time("소요시간")
				.exp(plogging.getExp())
				.build();
	}

}
