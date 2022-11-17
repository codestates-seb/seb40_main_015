package com.dongnebook.domain.rental.dto.Request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RentalRegisterRequest {

    @NotEmpty
    private Long bookId;

    @NotEmpty
    private Long memberId;

    @Builder
    public RentalRegisterRequest(Long bookId, Long memberId){
        this.bookId = bookId;
        this.memberId = memberId;
    }

}
