package com.ssafy.forestkeeper.domain.repository.mountain;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.mountain.QMountain;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

    public List<Tuple> findByDistance(double lat, double lng) {

        NumberExpression<Double> dist = MathExpressions.sin(
                Expressions.constant(lat * Math.PI / 180.0))
            .multiply(MathExpressions.sin(qMountain.lat.multiply(Math.PI).divide(180.0)))
            .add(MathExpressions.cos(Expressions.constant(lat * Math.PI / 180.0))
                .multiply(MathExpressions.cos(qMountain.lat.multiply(Math.PI).divide(180.0)))
                .multiply(MathExpressions.cos(
                    qMountain.lng.subtract(Expressions.constant(lng)).multiply(Math.PI)
                        .divide(180.0))
                ));

        dist = MathExpressions.acos(dist).multiply(180.0).divide(Math.PI)
            .multiply(Expressions.constant(60 * 1.1515 * 1.609344));

        List<Tuple> tuples = jpaQueryFactory.select(dist.as("distance"), qMountain)
            .from(qMountain).orderBy(dist.asc()).limit(5).fetch();

        return tuples;
    }

    public List<Tuple> findByHeight(double avg) {

        NumberExpression<Double> dif = qMountain.height.subtract(avg).abs();

        return jpaQueryFactory.select(dif, qMountain).from(qMountain).orderBy(dif.asc()).limit(5).fetch();
    }
}
