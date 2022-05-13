package com.ssafy.api.domain.repository.mountain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.domain.dao.mountain.Mountain;
import com.ssafy.api.domain.dao.mountain.QMountain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MountainRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QMountain qMountain = QMountain.mountain;

    public List<Mountain> findByNameContains(String keyword, Pageable pageable) {

        return this.jpaQueryFactory
            .selectFrom(qMountain)
            .where(qMountain.name.contains(keyword))
            .orderBy(qMountain.name.asc())
            .offset(pageable.getPageNumber())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<Mountain> findByNameContains(String keyword) {

        return this.jpaQueryFactory
            .selectFrom(qMountain)
            .where(qMountain.name.contains(keyword))
            .fetch();
    }
}
