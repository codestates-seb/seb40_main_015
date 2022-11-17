package com.dongnebook.domain.rental.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentalQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
