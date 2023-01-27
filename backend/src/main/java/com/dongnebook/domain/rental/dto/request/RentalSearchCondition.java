package com.dongnebook.domain.rental.dto.request;

import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.global.utils.Enum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RentalSearchCondition {
    @Enum(enumClass = RentalState.class, ignoreCase = false)
    private String rentalState;

    public RentalSearchCondition(String rentalState){
        this.rentalState = rentalState;
    }
}
