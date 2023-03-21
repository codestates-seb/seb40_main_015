package com.dongnebook.domain.rental.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.rental.dto.response.QRentalInfoResponse is a Querydsl Projection type for RentalInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRentalInfoResponse extends ConstructorExpression<RentalInfoResponse> {

    private static final long serialVersionUID = -1213194668L;

    public QRentalInfoResponse(com.querydsl.core.types.Expression<Long> rentalId, com.querydsl.core.types.Expression<String> customerName, com.querydsl.core.types.Expression<com.dongnebook.domain.rental.domain.RentalState> rentalState, com.querydsl.core.types.Expression<java.time.LocalDateTime> rentalStartedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> rentalDeadline, com.querydsl.core.types.Expression<java.time.LocalDateTime> rentalReturnedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> rentalCanceledAt) {
        super(RentalInfoResponse.class, new Class<?>[]{long.class, String.class, com.dongnebook.domain.rental.domain.RentalState.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, rentalId, customerName, rentalState, rentalStartedAt, rentalDeadline, rentalReturnedAt, rentalCanceledAt);
    }

}

