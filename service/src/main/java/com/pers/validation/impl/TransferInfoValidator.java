package com.pers.validation.impl;

import com.pers.dto.TransferCreateDto;
import com.pers.validation.TransferInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransferInfoValidator implements ConstraintValidator<TransferInfo, TransferCreateDto> {


    @Override
    public boolean isValid(TransferCreateDto value, ConstraintValidatorContext context) {
//        return value.amount().compareTo(BigDecimal.ZERO) > 0  && value.cardIdTo() != 0;
        return true;
    }
}
