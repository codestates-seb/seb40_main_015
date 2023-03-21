package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.member.dto.response.QBookDetailMemberResponse is a Querydsl Projection type for BookDetailMemberResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBookDetailMemberResponse extends ConstructorExpression<BookDetailMemberResponse> {

    private static final long serialVersionUID = 892223596L;

    public QBookDetailMemberResponse(com.querydsl.core.types.Expression<Long> merchantId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Double> grade, com.querydsl.core.types.Expression<String> avatarUrl) {
        super(BookDetailMemberResponse.class, new Class<?>[]{long.class, String.class, double.class, String.class}, merchantId, name, grade, avatarUrl);
    }

}

