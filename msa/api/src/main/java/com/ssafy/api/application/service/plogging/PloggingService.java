package com.ssafy.api.application.service.plogging;

import com.ssafy.api.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.api.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.api.application.dto.response.plogging.PloggingCumulativeResponseDTO;
import com.ssafy.api.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.api.domain.dao.mountain.TrashCan;
import com.ssafy.api.domain.dao.plogging.Plogging;

import java.util.List;
import java.util.Optional;

public interface PloggingService {
	
	PloggingDetailResponseDTO get(String ploggingId);
    Plogging register(PloggingRegisterDTO ploggingRegisterDTO, String email);
    void registerExp(ExpRegisterDTO expRegisterDTO);
    List<TrashCan> getTrashCanList();
    Optional<List<TrashCan>> getTrashCanList(String region);
    void registerPloggingImg(String originalFileName, String savedFileName, Plogging plogging);
    PloggingCumulativeResponseDTO getCumulative(String mountainName);
}
