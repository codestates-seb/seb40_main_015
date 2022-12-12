package com.dongnebook.domain.review.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRequest {
    @NotNull
    @Positive
    private Long bookId;

    @NotEmpty
    @Size(max = 30)
    private String reviewMessage;

    @Range(min= 1, max= 5)
    private Long grade;

    @Builder
    public ReviewRequest(Long bookId, String reviewMessage, Long grade){
        this.bookId = bookId;
        this.reviewMessage = reviewMessage;
        this.grade = grade;
    }
}
