package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferReadDto(
        Long id,
        Long clientId,

        Long cardIdFrom,
        Long cardIdTo,
        BigDecimal amount,
        Instant timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
