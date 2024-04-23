package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record ReplenishmentReadDto(
        Long id,
        Long clientId,
        Long cardNo,
        BigDecimal amount,
        Instant timeOfReplenishment,
        Status status

) {
}
