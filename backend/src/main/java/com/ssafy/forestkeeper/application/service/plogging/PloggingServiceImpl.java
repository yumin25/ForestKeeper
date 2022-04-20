package com.ssafy.forestkeeper.application.service.plogging;

import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
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
		User user = userRepository.getById(ploggingRegisterDTO.getUser_id());
		Mountain mountain = mountainRepository.getById(ploggingRegisterDTO.getMountain_id());
		Plogging plogging = new Plogging(ploggingRegisterDTO.getDistance(), ploggingRegisterDTO.getStart_time(), ploggingRegisterDTO.getEnd_time(), 0L, user, mountain);
		ploggingRepository.save(plogging);
	}

}
