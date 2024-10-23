package com.pers.dto;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class SmsCodeDto {

    @Length(min = 4, max = 4, message = "Введите 6-значный цифровой код")
    private int value;
}
