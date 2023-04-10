package com.dongnebook.domain.rental.dto.response;

import com.dongnebook.domain.book.application.port.in.response.BookInfoResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class RentalBookResponse{
    private BookInfoResponse bookInfo;
    private RentalInfoResponse rentalInfo;

    @QueryProjection
    public RentalBookResponse(BookInfoResponse bookInfo, RentalInfoResponse rentalInfo){
        this.bookInfo = bookInfo;
        this.rentalInfo = rentalInfo;
    }
}