package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentCreateDto(String shopName,
                               BigDecimal amount,
                               Long clientId,
                               Long cardId,
                               LocalDateTime timeOfPay,
                               Status status) {
}
