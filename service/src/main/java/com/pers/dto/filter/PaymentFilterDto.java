package com.pers.dto.filter;

import com.pers.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
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
