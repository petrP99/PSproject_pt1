package com.pers.dto;

import com.pers.entity.Status;
import com.pers.validation.ReplenishmentInfo;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;

@ReplenishmentInfo
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
