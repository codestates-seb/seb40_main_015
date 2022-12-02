package com.dongnebook.global.utils;

import com.dongnebook.domain.rental.exception.RentalNotFoundException;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(ObjectUtils.isEmpty(value)) return true;

        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if(enumValues != null) {
            for(Object enumValue : enumValues) {
                if(value.equals(enumValue.toString())
                    || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
