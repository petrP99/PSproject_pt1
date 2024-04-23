package com.pers.validation.impl;

import com.pers.dto.PaymentCreateDto;
import com.pers.validation.PaymentInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import static org.springframework.util.StringUtils.hasText;

public class PaymentInfoValidator implements ConstraintValidator<PaymentInfo, PaymentCreateDto> {
    @Override
    public boolean isValid(PaymentCreateDto value, ConstraintValidatorContext context) {
        return hasText(String.valueOf(value.amount())) && hasText(String.valueOf(value.cardId()));
    }
}
