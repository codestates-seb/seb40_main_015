package com.dongnebook.domain.reservation.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponse {

    private Long reservationId;

    private LocalDateTime rentalExpectedAt;

    @QueryProjection
    public ReservationResponse(Long reservationId, LocalDateTime rentalExpectedAt){
        this.reservationId = reservationId;
        this.rentalExpectedAt = rentalExpectedAt;
    }
}
