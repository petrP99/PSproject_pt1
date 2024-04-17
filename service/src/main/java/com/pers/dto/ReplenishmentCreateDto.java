package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReplenishmentCreateDto(
        Long clientId,
        Long cardId,
        @Positive
        BigDecimal amount,
        LocalDateTime timeOfReplenishment,
        Status status

) {
}
