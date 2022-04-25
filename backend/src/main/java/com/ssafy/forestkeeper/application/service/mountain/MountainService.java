package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import java.util.Optional;

public interface MountainService {

    Optional<Mountain> getMountainInfo(String mountainCode);
}
