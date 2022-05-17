package com.ssafy.forestkeeper.application.service.userinfo;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingGetListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserPloggingInfoDTO;
import com.ssafy.forestkeeper.domain.dao.image.Image;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;
import com.ssafy.forestkeeper.exception.MountainNotFoundException;
import com.ssafy.forestkeeper.exception.PloggingNotFoundException;
import com.ssafy.forestkeeper.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final PloggingRepository ploggingRepository;

    private final UserRepository userRepository;

    private final MountainRepository mountainRepository;

    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.hosting}")
    public String hosting;

    @Override
    public PloggingGetListWrapperResponseDTO getPloggingList(int page) {

        List<Plogging> ploggingList = ploggingRepository.findByUserOrderByStartTimeDesc(
                        userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")), PageRequest.of(page - 1, 10))
                .orElseThrow(() -> new PloggingNotFoundException("글 정보가 존재하지 않습니다."));

        return convertPloggingListToDTO(ploggingList);

    }

    private PloggingGetListWrapperResponseDTO convertPloggingListToDTO(List<Plogging> ploggingList) {

        List<PloggingGetListResponseDTO> ploggingListResponseDTOGetList = new ArrayList<>();

        Image image;
        String imagePath;

        for (Plogging plogging : ploggingList) {
            image = imageRepository.findByPlogging(plogging).orElse(null);

            if (image == null) imagePath = "";
            else imagePath = hosting + "thumb/" + image.getSavedFileName();

            ploggingListResponseDTOGetList.add(
                    PloggingGetListResponseDTO.builder()
                            .date(plogging.getStartTime().toLocalDate().toString())
                            .ploggingId(plogging.getId())
                            .distance(plogging.getDistance())
                            .time(plogging.getDurationTime())
                            .exp(plogging.getExp())
                            .mountainName(plogging.getMountain().getName())
                            .imagePath(imagePath)
                            .build());
        }

        return PloggingGetListWrapperResponseDTO.builder()
                .list(ploggingListResponseDTOGetList)
                .build();

    }

    @Override
    public MountainUserInfoWrapperResponseDTO getMountainList(int page) {

        Map<String, String> map = new HashMap<>();

        List<Plogging> ploggingList = ploggingRepository.findByUser(
                        userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .orElseThrow(() -> new PloggingNotFoundException("플로깅 정보가 존재하지 않습니다."));

        ploggingList.forEach(plogging ->
                map.put(plogging.getMountain().getCode(), plogging.getMountain().getName())
        );

        List<MountainUserInfoResponseDTO> list = new ArrayList<>();

        for (String key : map.keySet()) {
            list.add(MountainUserInfoResponseDTO.builder().mountainCode(key).mountainName(map.get(key)).build());
        }

        return MountainUserInfoWrapperResponseDTO.builder()
                .list(list)
                .build();

    }

    //유저상세 페이지내 산별 플로깅 목록
    @Override
    public PloggingGetListWrapperResponseDTO getPloggingInMountain(String mountainCode) {

        Mountain mountain = mountainRepository.findByCode(mountainCode)
                .orElseThrow(() -> new MountainNotFoundException("산 정보가 존재하지 않습니다."));

        List<Plogging> ploggingList = ploggingRepository.findByUserAndMountainOrderByStartTimeDesc(
                        userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")), mountain)
                .orElseThrow(() -> new PloggingNotFoundException("플로깅 정보가 존재하지 않습니다."));

        return convertPloggingListToDTO(ploggingList);
    }

    @Override
    public UserPloggingInfoDTO getUserAccumulative() {

        List<Plogging> ploggingList = ploggingRepository.findByUser(
                        userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(), false)
                                .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다.")))
                .orElseThrow(() -> new PloggingNotFoundException("플로깅 정보가 존재하지 않습니다."));

        double distance = 0;
        int exp = 0;

        String[] str;
        int hour = 0;
        int minute = 0;

        for (Plogging plogging : ploggingList) {
            distance += plogging.getDistance();
            exp += plogging.getExp();

            str = plogging.getDurationTime().split(" : ");
            hour += Integer.parseInt(str[0]);
            minute += Integer.parseInt(str[1]);
        }

        String time = calcDuration(hour, minute);

        return UserPloggingInfoDTO.builder()
                .distance(distance)
                .time(time)
                .exp(exp)
                .build();

    }

    public String calcDuration(int hour, int minute) {

        StringBuilder sb = new StringBuilder();

        hour += minute / 60;
        minute = minute % 60;

        sb.append(hour).append(" : ");

        if (minute < 10) sb.append(0).append(minute);
        else sb.append(minute);

        return sb.toString();

    }

}
