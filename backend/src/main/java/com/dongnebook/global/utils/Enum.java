package com.dongnebook.global.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {EnumValidator.class})
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {
    String message() default "Enum에 없는 값입니다.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum> enumClass();
    boolean ignoreCase() default false;
}
