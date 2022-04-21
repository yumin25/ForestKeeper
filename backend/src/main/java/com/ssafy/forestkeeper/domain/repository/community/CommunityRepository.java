package com.ssafy.forestkeeper.domain.repository.community;

import com.ssafy.forestkeeper.domain.dao.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, String> {

    Optional<Community> findByIdAndDelete(String communityId, boolean delete);

}
