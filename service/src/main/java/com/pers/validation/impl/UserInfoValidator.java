package com.pers.validation.impl;

import com.pers.dto.UserCreateDto;
import com.pers.validation.UserInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateDto> {

    @Override
    public boolean isValid(UserCreateDto value, ConstraintValidatorContext context) {
        return hasText(value.getLogin()) && hasText(value.getRawPassword());
    }
}
