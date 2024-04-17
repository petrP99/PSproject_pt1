package com.pers.dto.filter;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentFilterDto(
        Long id,
        String shopName,
        BigDecimal amount,
        Long clientId,
        Long cardId,
        LocalDateTime timeOfPay,
        Status status
) {
}
