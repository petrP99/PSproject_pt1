package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentReadDto(Long id,
                             String shopName,
                             BigDecimal amount,
                             Long clientId,
                             Long cardId,
                             Instant timeOfPay,
                             Status status) {
}
