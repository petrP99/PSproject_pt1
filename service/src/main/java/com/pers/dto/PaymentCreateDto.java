package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentCreateDto(String shopName,
                               @Positive
                               BigDecimal amount,
                               Long clientId,
                               Long cardId,
                               LocalDateTime timeOfPay,
                               Status status) {
}
