package com.dongnebook.domain.rental.dto.response;

import java.time.LocalDateTime;

import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class RentalInfoResponse {
    private Long rentalId;
    private String customerName;
    private RentalState rentalState;
    private LocalDateTime rentalStartedAt;
    private LocalDateTime rentalDeadline;
    private LocalDateTime rentalReturnedAt;
    private LocalDateTime rentalCanceledAt;

    @QueryProjection
    public RentalInfoResponse(Long rentalId, String customerName, RentalState rentalState,
                              LocalDateTime rentalStartedAt, LocalDateTime rentalDeadline,
                              LocalDateTime rentalReturnedAt, LocalDateTime rentalCanceledAt) {
        this.rentalId = rentalId;
        this.customerName = customerName;
        this.rentalState = rentalState;
        this.rentalStartedAt = rentalStartedAt;
        this.rentalDeadline = rentalDeadline;
        this.rentalReturnedAt = rentalReturnedAt;
        this.rentalCanceledAt = rentalCanceledAt;
    }

    public static RentalInfoResponse of(Rental rental) {
        return new RentalInfoResponse(rental.getId(), rental.getCustomer().getNickname(),
                rental.getRentalState(), rental.getRentalStartedAt(), rental.getRentalDeadLine(),
                rental.getRentalReturnedAt(), rental.getCanceledAt());
    }
}
