package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.member.dto.response.QMemberDetailResponse is a Querydsl Projection type for MemberDetailResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberDetailResponse extends ConstructorExpression<MemberDetailResponse> {

    private static final long serialVersionUID = -755156829L;

    public QMemberDetailResponse(com.querydsl.core.types.Expression<Long> memberId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<? extends com.dongnebook.domain.model.Location> location, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<Integer> totalBookCount, com.querydsl.core.types.Expression<String> avatarUrl, com.querydsl.core.types.Expression<Double> avgGrade) {
        super(MemberDetailResponse.class, new Class<?>[]{long.class, String.class, com.dongnebook.domain.model.Location.class, String.class, int.class, String.class, double.class}, memberId, name, location, address, totalBookCount, avatarUrl, avgGrade);
    }

}

