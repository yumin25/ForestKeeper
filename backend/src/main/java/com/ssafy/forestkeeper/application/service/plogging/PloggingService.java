package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.MountainPloggingInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

import java.util.List;
import java.util.Optional;

public interface PloggingService {

    PloggingDetailResponseDTO get(String ploggingId);

    Plogging register(PloggingRegisterRequestDTO ploggingRegisterRequestDTO);

    void registerExp(ExpRegisterRequestDTO expRegisterRequestDTO);

    TrashCanListWrapperResponseDTO getTrashCanList();

    TrashCanListWrapperResponseDTO getTrashCanList(String region);

    void registerPloggingImg(String originalFileName, String savedFileName, String ploggingId);

    MountainPloggingInfoResponseDTO getMountainPlogging(String mountainName);

}
