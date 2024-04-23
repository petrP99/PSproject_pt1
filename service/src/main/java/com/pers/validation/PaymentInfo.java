package com.pers.validation;

import com.pers.validation.impl.PaymentInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = PaymentInfoValidator.class)
public @interface PaymentInfo {

    String message() default "All fields must be completed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
