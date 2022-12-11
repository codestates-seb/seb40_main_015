package com.dongnebook.domain.reservation.dto.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

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
