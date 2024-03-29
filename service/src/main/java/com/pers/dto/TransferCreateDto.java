package com.pers.dto;

import com.pers.entity.Status;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferCreateDto(
        Long cardNoFrom,
        Long cardNoTo,
        BigDecimal amount,
        LocalDateTime timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
