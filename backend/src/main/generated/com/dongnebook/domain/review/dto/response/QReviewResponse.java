package com.dongnebook.domain.review.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.review.dto.response.QReviewResponse is a Querydsl Projection type for ReviewResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewResponse extends ConstructorExpression<ReviewResponse> {

    private static final long serialVersionUID = 2116302958L;

    public QReviewResponse(com.querydsl.core.types.Expression<Long> reviewId, com.querydsl.core.types.Expression<Long> reviewerId, com.querydsl.core.types.Expression<String> customerNickname, com.querydsl.core.types.Expression<String> customerAvatarUrl, com.querydsl.core.types.Expression<String> reviewMessage, com.querydsl.core.types.Expression<Long> reviewGrade, com.querydsl.core.types.Expression<String> bookTitle, com.querydsl.core.types.Expression<String> bookUrl, com.querydsl.core.types.Expression<Long> bookId, com.querydsl.core.types.Expression<Long> rentalId) {
        super(ReviewResponse.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, long.class, String.class, String.class, long.class, long.class}, reviewId, reviewerId, customerNickname, customerAvatarUrl, reviewMessage, reviewGrade, bookTitle, bookUrl, bookId, rentalId);
    }

}

