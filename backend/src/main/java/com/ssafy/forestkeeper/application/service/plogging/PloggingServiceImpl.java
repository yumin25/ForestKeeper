package com.ssafy.forestkeeper.application.service.plogging;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.request.plogging.Coordinates;
import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.MountainPloggingInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
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
	
	private final ImageRepository imageRepository;
	
    @Value("${cloud.aws.s3.hosting}")
    public String hosting;


	@Override
	public Plogging register(PloggingRegisterDTO ploggingRegisterDTO) {
		Mountain mountain = mountainRepository.findByCode(ploggingRegisterDTO.getMountainCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		Duration duration = Duration.between(LocalDateTime.parse(ploggingRegisterDTO.getStartTime(), formatter), LocalDateTime.parse(ploggingRegisterDTO.getEndTime(), formatter));
			
		Plogging plogging = Plogging.builder()
								.distance(ploggingRegisterDTO.getDistance())
								.startTime(LocalDateTime.parse(ploggingRegisterDTO.getStartTime(), formatter))
								.endTime(LocalDateTime.parse(ploggingRegisterDTO.getEndTime(), formatter))
								.exp(0L)
								.durationTime(getDuration(duration))
								.user(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false).get())
								.mountain(mountain)
								.coords(getCoords(ploggingRegisterDTO.getCoords()))
								.build();
		return ploggingRepository.save(plogging);
	}
	
	public String getCoords(List<Coordinates> list) {
    	StringBuilder sb = new StringBuilder();
    	for(Coordinates c : list) {
    		sb.append(c.getX()).append(",").append(c.getY()).append("/");
    	}
    	
    	return sb.toString();
	}
	
	public String getDuration(Duration duration) {
		StringBuilder sb = new StringBuilder();
		String HM = duration.toString().split("T")[1];
		int M;
		if(duration.toString().contains("H")) {
			sb.append(HM.split("H")[0]);
			sb.append(" : ");
			M = Integer.parseInt(HM.split("H")[1].split("M")[0]);
			if(M < 10) sb.append(0).append(M);
			else sb.append(M);
		}else if(duration.toString().contains("M")) {
			sb.append("0 : ");
			M = Integer.parseInt(HM.split("M")[0]);
			if(M < 10) sb.append(0).append(M);
			else sb.append(M);
		}
		else {
			sb.append("0 : 00");
		}
		
		
		return sb.toString();
	}

	@Override
	public PloggingDetailResponseDTO get(String ploggingId) {
		Plogging plogging = ploggingRepository.findById(ploggingId)
				.orElseThrow(() -> new IllegalArgumentException("해당 플로깅을 찾을 수 없습니다."));
		Image image = imageRepository.findByPloggingId(ploggingId).orElse(null);
		String imagePath;
		if(image == null) imagePath = "";
		else imagePath = hosting + "plogging/" +image.getSavedFileName();
		
		List<Coordinates> list = new ArrayList<>();
		String[] coords = plogging.getCoords().split("/");
		String[] xy;
		for(String s : coords) {
			xy = s.split(",");
			list.add(new Coordinates(xy[0],xy[1],xy[0],xy[1]));
		}
		return PloggingDetailResponseDTO.builder()
				.date(plogging.getStartTime().toLocalDate().toString())
				.mountainName(plogging.getMountain().getName())
				.distance(plogging.getDistance())
				.time(plogging.getDurationTime())
				.exp(plogging.getExp())
				.imagePath(imagePath)
				.coords(list)
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
	public void registerPloggingImg(String originalFileName, String savedFileName, String ploggingId) {
		Plogging plogging = ploggingRepository.findById(ploggingId)
				.orElseThrow(() -> new IllegalArgumentException("해당 플로깅 기록을 찾을 수 없습니다."));
    	imageRepository.save(Image.builder()
				.originalFileName(originalFileName)
				.savedFileName(savedFileName)
				.plogging(plogging)
				.build());
	}

	//산상세페이지 플로깅 관련 정보
	@Override
	public MountainPloggingInfoResponseDTO getMountainPlogging(String mountainCode) {
		Mountain mountain = mountainRepository.findByCode(mountainCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
		User user = userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false).get();
		
		List<Plogging> ploggingList = ploggingRepository.findByMountainId(mountain.getId()).orElse(null);
		long distance = 0L;
		int visiter = 0;
		List<Plogging> visitList = ploggingRepository.findByUserIdAndMountainIdOrderByStartTimeDesc(user.getId(), mountain.getId()).orElse(new ArrayList<Plogging>());
		
		if(ploggingList == null) {
			return MountainPloggingInfoResponseDTO.builder()
					.distance(distance)
					.visiter(visiter)
					.count(visitList.size())
					.build();
		}

		for(Plogging plogging : ploggingList) {
			visiter++;
			distance += (int)plogging.getDistance();
		}
		return MountainPloggingInfoResponseDTO.builder()
											.distance(distance)
											.visiter(visiter)
											.count(visitList.size())
											.build();
	}
}
