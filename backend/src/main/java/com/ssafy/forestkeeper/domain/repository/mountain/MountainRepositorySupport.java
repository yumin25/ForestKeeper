package com.ssafy.forestkeeper.domain.repository.mountain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.mountain.QMountain;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MountainRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QMountain qMountain = QMountain.mountain;

    public List<Mountain> findByNameContains(String keyword) {
        System.out.println(keyword);
        return this.jpaQueryFactory
            .selectFrom(qMountain)
            .where(qMountain.name.contains(keyword))
            .fetch();
    }
}
