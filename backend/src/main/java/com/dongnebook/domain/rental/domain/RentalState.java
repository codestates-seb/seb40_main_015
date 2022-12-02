package com.dongnebook.domain.rental.domain;

public enum RentalState {

    TRADING("거래중"),
    BEING_RENTED("대여중"),
    RETURN_UNREVIEWED("반납완료&리뷰미완료"),
    RETURN_REVIEWED("반납완료&리뷰완료"),
    CANCELED("대여취소"),
    ALL("전체");

    private String message;

    RentalState(String message) {
        this.message = message;
    }

}
