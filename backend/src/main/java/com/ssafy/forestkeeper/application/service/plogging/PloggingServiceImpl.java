package com.ssafy.forestkeeper.application.service.plogging;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
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
		Duration duration = Duration.between(ploggingRegisterDTO.getStartTime(), ploggingRegisterDTO.getEndTime());
		StringBuilder sb = new StringBuilder();
		String HM = duration.toString().split("T")[1];
		if(duration.toString().contains("H")) {
			sb.append(HM.split("H")[0]);
			sb.append(" : ");
			sb.append(HM.split("H")[1].split("M")[0]);
		}else {
			sb.append("0 : ");
			sb.append(HM.split("M")[0]);
		}
		Plogging plogging = Plogging.builder()
								.distance(ploggingRegisterDTO.getDistance())
								.startTime(ploggingRegisterDTO.getStartTime())
								.endTime(ploggingRegisterDTO.getEndTime())
								.exp(0L)
								.durationTime(sb.toString())
								.user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false).get())
								.mountain(mountainRepository.findByName(ploggingRegisterDTO.getMountainName()))
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
				.time(plogging.getDurationTime())
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
	public Optional<List<TrashCan>> getTrashCanList(String region) {

		return trashCanRepository.findByRegion(region);
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
