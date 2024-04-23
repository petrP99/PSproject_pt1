package com.pers.dto.filter;

import com.pers.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransferFilterDto(
        Long id,
        Long clientId,
        Long cardNoFrom,
        Long cardNoTo,
        BigDecimal amount,
        LocalDateTime timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
