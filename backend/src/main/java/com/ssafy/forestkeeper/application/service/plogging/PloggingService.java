package com.ssafy.forestkeeper.application.service.plogging;

import java.util.List;
import java.util.Optional;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingCumulativeResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;

public interface PloggingService {
	
	PloggingDetailResponseDTO get(String ploggingId);
    Plogging register(PloggingRegisterDTO ploggingRegisterDTO);
    void registerExp(ExpRegisterDTO expRegisterDTO);
    List<TrashCan> getTrashCanList();
    Optional<List<TrashCan>> getTrashCanList(String region);
    void registerPloggingImg(String originalFileName, String savedFileName, Plogging plogging);
    PloggingCumulativeResponseDTO getCumulative(String mountainName);
}
