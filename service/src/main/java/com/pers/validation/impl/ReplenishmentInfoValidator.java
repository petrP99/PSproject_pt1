package com.pers.validation.impl;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.validation.ReplenishmentInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

public class ReplenishmentInfoValidator implements ConstraintValidator<ReplenishmentInfo, ReplenishmentCreateDto> {
    @Override
    public boolean isValid(ReplenishmentCreateDto value, ConstraintValidatorContext context) {
        return hasText(String.valueOf(value.amount())) && hasText(String.valueOf(value.cardId()));
    }
}
