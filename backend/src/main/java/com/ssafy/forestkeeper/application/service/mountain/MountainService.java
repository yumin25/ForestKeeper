package com.ssafy.forestkeeper.application.service.mountain;

import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import java.util.List;
import java.util.Optional;

public interface MountainService {

    Optional<Mountain> getMountainInfo(String mountainCode);
    Optional<List<Mountain>> searchMountain(String keyword, int page);
    int totalSearch(String keyword);
}
