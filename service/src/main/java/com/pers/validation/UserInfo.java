package com.pers.validation;

import com.pers.validation.impl.*;
import jakarta.validation.*;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = UserInfoValidator.class)
public @interface UserInfo {

    String message() default "Login and password should be filled in";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
