package com.dongnebook.domain.rental.dto.Response;

import com.dongnebook.domain.book.dto.response.BookInfoResponse;
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