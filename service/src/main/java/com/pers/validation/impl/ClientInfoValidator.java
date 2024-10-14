package com.pers.validation.impl;

import com.pers.dto.ClientCreateDto;
import com.pers.validation.ClientInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

public class ClientInfoValidator implements ConstraintValidator<ClientInfo, ClientCreateDto> {


    @Override
    public boolean isValid(ClientCreateDto value, ConstraintValidatorContext context) {
        return hasText(value.phone()) && hasText(value.firstName()) && hasText(value.lastName());
    }
}
