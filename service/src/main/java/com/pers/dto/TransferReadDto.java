package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferReadDto(
        Long id,
        Long cardNoFrom,
        Long cardNoTo,
        BigDecimal amount,
        LocalDateTime timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
