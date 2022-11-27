package com.dongnebook.domain.reservation.dto.request;

import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ReservationInfoResponse {

    private ReservationResponse reservationInfo;

    private BookSimpleResponse bookInfo;

    @QueryProjection
    public ReservationInfoResponse(ReservationResponse reservationInfo,
                                   BookSimpleResponse bookInfo){
        this.reservationInfo = reservationInfo;
        this.bookInfo = bookInfo;
    }

}
