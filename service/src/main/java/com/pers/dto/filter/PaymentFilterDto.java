package com.pers.dto.filter;

import com.pers.dto.UserReadDto;
import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentFilterDto(
        String shopName,
        BigDecimal amount,
        Long clientId,
        Long cardId,
        LocalDateTime timeOfPay,
        Status status
) {
}
