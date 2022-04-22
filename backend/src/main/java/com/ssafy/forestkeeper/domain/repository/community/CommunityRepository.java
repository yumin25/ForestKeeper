package com.ssafy.forestkeeper.domain.repository.community;

import com.ssafy.forestkeeper.domain.dao.community.Community;
import com.ssafy.forestkeeper.domain.enums.CommunityCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, String> {

    Optional<Community> findByIdAndDelete(String communityId, boolean delete);

    Optional<List<Community>> findByCommunityCodeAndDeleteOrderByCreateTimeDesc(CommunityCode communityCode, boolean delete, Pageable pageable);

}
