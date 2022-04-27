package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.comment.CommentGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.enums.RegionCode;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import com.ssafy.forestkeeper.domain.repository.trashcan.TrashCanRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PloggingServiceImpl implements PloggingService{
	
    private final PloggingRepository ploggingRepository;
	
    private final UserRepository userRepository;
	
	private final MountainRepository mountainRepository;
	
	private final TrashCanRepository trashCanRepository;

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
		Plogging plogging = ploggingRepository.findById(ploggingId)
				.orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
		return PloggingDetailResponseDTO.builder()
				.date(plogging.getStartTime().toLocalDate().toString())
				.mountainName(plogging.getMountain().getName())
				.distance(plogging.getDistance())
				.time("소요시간")
				.exp(plogging.getExp())
				.build();
	}

	@Override
	public void registerExp(ExpRegisterDTO expRegisterDTO) {
		Plogging plogging = ploggingRepository.findById(expRegisterDTO.getPloggingId())
				.orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
		plogging.setExp(expRegisterDTO.getExp());
		ploggingRepository.save(plogging);
	}

	@Override
	public List<TrashCan> getTrashCanList() {

		return trashCanRepository.findAll();
	}

	@Override
	public TrashCanListWrapperResponseDTO getTrashCanList(String region) {
		return null;
	}

//	@Override
//	public TrashCanListWrapperResponseDTO getTrashCanList(String regionName) {
//		List<TrashCanDTO> trashCanDTOList = new ArrayList<>();
//		trashCanRepository.findByRegionCode(RegionCode.valueOf(regionName)).get().forEach(tc ->{
//			trashCanDTOList.add(TrashCanDTO.builder()
//					.address(tc.getAddress())
//					.latitude(tc.getLatitude())
//					.longitude(tc.getLongitude())
//					.BEtype(tc.getType())
//					.build());
//		});
//
//		return TrashCanListWrapperResponseDTO.builder()
//				.list(trashCanDTOList)
//				.build();
//	}

}
