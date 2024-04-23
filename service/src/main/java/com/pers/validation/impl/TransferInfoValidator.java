package com.pers.validation.impl;

import com.pers.dto.TransferCreateDto;
import com.pers.validation.TransferInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import static org.springframework.util.StringUtils.hasText;

public class TransferInfoValidator implements ConstraintValidator<TransferInfo, TransferCreateDto> {


    @Override
    public boolean isValid(TransferCreateDto value, ConstraintValidatorContext context) {
        return hasText(String.valueOf(value.amount())) && hasText(String.valueOf(value.cardIdTo()));
    }
}
