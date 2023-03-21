package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.member.dto.response.QMemberResponse is a Querydsl Projection type for MemberResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberResponse extends ConstructorExpression<MemberResponse> {

    private static final long serialVersionUID = 2075878898L;

    public QMemberResponse(com.querydsl.core.types.Expression<Long> merchantId, com.querydsl.core.types.Expression<String> merchantName, com.querydsl.core.types.Expression<? extends com.dongnebook.domain.model.Location> location) {
        super(MemberResponse.class, new Class<?>[]{long.class, String.class, com.dongnebook.domain.model.Location.class}, merchantId, merchantName, location);
    }

}

