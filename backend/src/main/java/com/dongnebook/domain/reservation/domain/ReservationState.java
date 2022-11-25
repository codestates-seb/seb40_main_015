package com.dongnebook.domain.reservation.domain;

public enum ReservationState {

    ON_RESERVATION("예약중"),
    CANCEL_RESERVATION("예약 취소"),
    RENTAL_OK("대여 진행");

    private String message;

    ReservationState(String message) {
        this.message = message;
    }
}
