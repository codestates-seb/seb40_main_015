package com.dongnebook.domain.reservation.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.reservation.dto.response.QReservationResponse is a Querydsl Projection type for ReservationResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReservationResponse extends ConstructorExpression<ReservationResponse> {

    private static final long serialVersionUID = 336484894L;

    public QReservationResponse(com.querydsl.core.types.Expression<Long> reservationId, com.querydsl.core.types.Expression<java.time.LocalDateTime> rentalExpectedAt) {
        super(ReservationResponse.class, new Class<?>[]{long.class, java.time.LocalDateTime.class}, reservationId, rentalExpectedAt);
    }

}

