package com.ssafy.forestkeeper.domain.repository.plogging;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.QPlogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PloggingRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QPlogging qPlogging = QPlogging.plogging;

    public List<Tuple> rankByDistance(Mountain mountain) {

        List<Tuple> tuples = jpaQueryFactory
                .select(qPlogging.user, qPlogging.distance.sum())
                .from(qPlogging)
                .where(qPlogging.mountain.eq(mountain))
                .groupBy(qPlogging.user)
                .orderBy(qPlogging.distance.sum().desc())
                .offset(0)
                .limit(10)
                .fetch();

        return tuples;

    }

    public List<Tuple> rankByCount(Mountain mountain) {

        List<Tuple> tuples = jpaQueryFactory
                .select(qPlogging.user, qPlogging.count())
                .from(qPlogging)
                .where(qPlogging.mountain.eq(mountain))
                .groupBy(qPlogging.user)
                .orderBy(qPlogging.count().desc())
                .offset(0)
                .limit(10)
                .fetch();

        return tuples;

    }

    public double getAvg(User user) {

        return jpaQueryFactory
                .select(qPlogging.mountain.height.avg())
                .from(qPlogging)
                .where(qPlogging.user.eq(user))
                .groupBy(qPlogging.user)
                .fetchOne();

    }

}
