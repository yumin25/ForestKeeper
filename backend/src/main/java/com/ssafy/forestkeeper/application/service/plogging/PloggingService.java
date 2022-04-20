package com.ssafy.forestkeeper.application.service.plogging;

import com.ssafy.forestkeeper.application.dto.request.plogging.PloggingRegisterDTO;

public interface PloggingService {

    void register(PloggingRegisterDTO ploggingRegisterDTO);
}
