package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferCreateDto(
        Long cardNoFrom,
        Long cardNoTo,
        BigDecimal amount,
        LocalDateTime timeOfTransfer,
        Status status
) {
}
