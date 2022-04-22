package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.request.plogging.ExpRegisterDTO;
import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingDetailResponseDTO;

public interface PloggingService {
	
	PloggingDetailResponseDTO get(String ploggingId);
    void register(PloggingRegisterDTO ploggingRegisterDTO);
    void registerExp(ExpRegisterDTO expRegisterDTO);
}
