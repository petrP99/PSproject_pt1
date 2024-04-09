package com.pers.validation.impl;

import com.pers.dto.*;
import com.pers.validation.*;
import jakarta.validation.*;
import liquibase.util.*;
import org.springframework.util.*;
import static org.springframework.util.StringUtils.hasText;

public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateDto> {

    @Override
    public boolean isValid(UserCreateDto value, ConstraintValidatorContext context) {
        return hasText(value.getLogin()) && hasText(value.getRawPassword());
    }
}
