package com.ssafy.api.application.service.plogging;

import com.ssafy.api.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.api.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.api.application.dto.response.plogging.PloggingCumulativeResponseDTO;
import com.ssafy.api.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.api.domain.dao.image.Image;
import com.ssafy.api.domain.dao.mountain.Mountain;
import com.ssafy.api.domain.dao.mountain.TrashCan;
import com.ssafy.api.domain.dao.plogging.Plogging;
import com.ssafy.api.domain.repository.image.ImageRepository;
import com.ssafy.api.domain.repository.mountain.MountainRepository;
import com.ssafy.api.domain.repository.plogging.PloggingRepository;
import com.ssafy.api.domain.repository.trashcan.TrashCanRepository;
import com.ssafy.api.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PloggingServiceImpl implements PloggingService{
	
    private final PloggingRepository ploggingRepository;
	
    private final UserRepository userRepository;
	
	private final MountainRepository mountainRepository;
	
	private final TrashCanRepository trashCanRepository;
	
	private final ImageRepository imageRepository;
	
    @Value("${cloud.aws.s3.hosting}")
    public String hosting;
    

	@Override
	public Plogging register(PloggingRegisterDTO ploggingRegisterDTO, String email) {
		Mountain mountain = mountainRepository.findByName(ploggingRegisterDTO.getMountainName())
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		Duration duration = Duration.between(LocalDateTime.parse(ploggingRegisterDTO.getStartTime(), formatter), LocalDateTime.parse(ploggingRegisterDTO.getEndTime(), formatter));
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
								.startTime(LocalDateTime.parse(ploggingRegisterDTO.getStartTime(), formatter))
								.endTime(LocalDateTime.parse(ploggingRegisterDTO.getEndTime(), formatter))
								.exp(0L)
								.durationTime(sb.toString())
								.user(userRepository.findByEmailAndDelete(email, false).get())
								.mountain(mountain)
								.build();
		return ploggingRepository.save(plogging);
	}

	@Override
	public PloggingDetailResponseDTO get(String ploggingId) {
		Plogging plogging = ploggingRepository.findById(ploggingId)
				.orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
		Image image = imageRepository.findByPloggingId(ploggingId).orElse(null);
		String imagePath;
		if(image == null) imagePath = "";
		else imagePath = hosting + image.getSavedFileName();
		return PloggingDetailResponseDTO.builder()
				.date(plogging.getStartTime().toLocalDate().toString())
				.mountainName(plogging.getMountain().getName())
				.distance(plogging.getDistance())
				.time(plogging.getDurationTime())
				.exp(plogging.getExp())
				.imagePath(imagePath)
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

	@Override
	public void registerPloggingImg(String originalFileName, String savedFileName, Plogging plogging) {
    	imageRepository.save(Image.builder()
				.originalFileName(originalFileName)
				.savedFileName(savedFileName)
				.plogging(plogging)
				.build());
	}

	@Override
	public PloggingCumulativeResponseDTO getCumulative(String mountainCode) {
		Mountain mountain = mountainRepository.findByCode(mountainCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
		List<Plogging> ploggingList = ploggingRepository.findByMountainId(mountain.getId()).orElse(null);
		long distance = 0L;
		int visiter = 0;
		
		if(ploggingList == null) {
			return PloggingCumulativeResponseDTO.builder()
					.distance(distance)
					.visiter(visiter)
					.build();
		}

		for(Plogging plogging : ploggingList) {
			visiter++;
			distance += (int)plogging.getDistance();
		}
		return PloggingCumulativeResponseDTO.builder()
											.distance(distance)
											.visiter(visiter)
											.build();
	}
}
