package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferCreateDto(
        Long clientId,
        Long cardIdFrom,
        Long cardIdTo,

        @Positive
        BigDecimal amount,
        Instant timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
