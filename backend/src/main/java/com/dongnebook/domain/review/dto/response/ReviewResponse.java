package com.dongnebook.domain.review.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ReviewResponse {
    private final Long reviewId;
    private final Long reviewerId;
    private final String customerNickname;
    private final String customerAvatarUrl;
    private final String reviewMessage;
    private final Long reviewGrade;
    private final String bookTitle;
    private final String bookUrl;
    private final Long bookId;
    private final Long rentalId;

    @QueryProjection
    public ReviewResponse(Long reviewId, Long reviewerId, String customerNickname, String customerAvatarUrl, String reviewMessage,
                          Long reviewGrade, String bookTitle, String bookUrl, Long bookId, Long rentalId){
        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
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
