package com.pers.dto.filter;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferFilterDto(
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
