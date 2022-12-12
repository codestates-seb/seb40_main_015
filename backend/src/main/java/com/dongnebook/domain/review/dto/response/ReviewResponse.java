package com.dongnebook.domain.review.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long reviewId;
    private String customerNickname;
    private String customerAvatarUrl;
    private String reviewMessage;
    private Long reviewGrade;
    private String bookTitle;
    private String bookUrl;
    private Long bookId;
    private Long rentalId;

    @QueryProjection
    public ReviewResponse(Long reviewId, String customerNickname, String customerAvatarUrl, String reviewMessage,
                          Long reviewGrade, String bookTitle, String bookUrl, Long bookId, Long rentalId){
        this.reviewId = reviewId;
        this.customerNickname = customerNickname;
        this.customerAvatarUrl = customerAvatarUrl;
        this.reviewMessage = reviewMessage;
        this.reviewGrade = reviewGrade;
        this.bookTitle = bookTitle;
        this.bookUrl = bookUrl;
        this.bookId = bookId;
        this.rentalId = rentalId;
    }
}
