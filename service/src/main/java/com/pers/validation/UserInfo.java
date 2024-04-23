package com.pers.validation;

import com.pers.validation.impl.UserInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = UserInfoValidator.class)
public @interface UserInfo {

    String message() default "Login and password should be filled in";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
