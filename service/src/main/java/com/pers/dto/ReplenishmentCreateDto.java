package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;

public record ReplenishmentCreateDto(
        Long clientId,
        Long cardId,

        @Positive
        BigDecimal amount,

        @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
        Instant timeOfReplenishment,
        Status status

) {
}
