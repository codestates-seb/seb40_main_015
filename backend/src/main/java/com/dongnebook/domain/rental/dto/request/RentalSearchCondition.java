package com.dongnebook.domain.rental.dto.request;

import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.global.utils.Enum;
import lombok.Getter;

@Getter
public class RentalSearchCondition {

    @Enum(enumClass = RentalState.class, ignoreCase = false)
    private final String rentalState;

    public RentalSearchCondition(String rentalState){
        this.rentalState = rentalState;
    }

}
