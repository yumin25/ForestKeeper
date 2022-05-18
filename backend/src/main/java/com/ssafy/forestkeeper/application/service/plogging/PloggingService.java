package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.MountainPloggingInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingTotalInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

public interface PloggingService {

    PloggingDetailResponseDTO get(String ploggingId);

    Plogging register(PloggingRegisterRequestDTO ploggingRegisterRequestDTO);

    void registerExp(ExpRegisterRequestDTO expRegisterRequestDTO);

    TrashCanListWrapperResponseDTO getTrashCanList();

    TrashCanListWrapperResponseDTO getTrashCanList(String region);

    void registerPloggingImg(String originalFileName, String savedFileName, String ploggingId);

    MountainPloggingInfoResponseDTO getMountainPlogging(String mountainName);

    PloggingTotalInfoResponseDTO getTotalInfo();

}
