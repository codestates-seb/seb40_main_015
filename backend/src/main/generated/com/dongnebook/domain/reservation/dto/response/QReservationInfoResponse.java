package com.dongnebook.domain.reservation.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.reservation.dto.response.QReservationInfoResponse is a Querydsl Projection type for ReservationInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReservationInfoResponse extends ConstructorExpression<ReservationInfoResponse> {

    private static final long serialVersionUID = -1376166292L;

    public QReservationInfoResponse(com.querydsl.core.types.Expression<? extends ReservationResponse> reservationInfo, com.querydsl.core.types.Expression<? extends com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse> bookInfo) {
        super(ReservationInfoResponse.class, new Class<?>[]{ReservationResponse.class, com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse.class}, reservationInfo, bookInfo);
    }

}

