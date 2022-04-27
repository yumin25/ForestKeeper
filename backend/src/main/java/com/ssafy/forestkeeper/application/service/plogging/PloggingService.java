package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.TrashCanListWrapperResponseDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.TrashCan;
import java.util.List;
import java.util.Optional;

public interface PloggingService {
	
	PloggingDetailResponseDTO get(String ploggingId);
    void register(PloggingRegisterDTO ploggingRegisterDTO);
    void registerExp(ExpRegisterDTO expRegisterDTO);
    List<TrashCan> getTrashCanList();
    Optional<List<TrashCan>> getTrashCanList(String region);
}
