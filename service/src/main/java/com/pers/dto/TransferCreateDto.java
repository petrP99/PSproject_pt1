package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferCreateDto(
        Long cardIdFrom,
        Long cardIdTo,
        @Positive
        BigDecimal amount,
        LocalDateTime timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
