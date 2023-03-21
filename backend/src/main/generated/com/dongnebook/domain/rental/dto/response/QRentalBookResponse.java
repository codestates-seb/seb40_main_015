package com.dongnebook.domain.rental.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.rental.dto.response.QRentalBookResponse is a Querydsl Projection type for RentalBookResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRentalBookResponse extends ConstructorExpression<RentalBookResponse> {

    private static final long serialVersionUID = 1547471247L;

    public QRentalBookResponse(com.querydsl.core.types.Expression<? extends com.dongnebook.domain.book.application.port.in.response.BookInfoResponse> bookInfo, com.querydsl.core.types.Expression<? extends RentalInfoResponse> rentalInfo) {
        super(RentalBookResponse.class, new Class<?>[]{com.dongnebook.domain.book.application.port.in.response.BookInfoResponse.class, RentalInfoResponse.class}, bookInfo, rentalInfo);
    }

}

