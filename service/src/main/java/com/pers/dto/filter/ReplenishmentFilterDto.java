package com.pers.dto.filter;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReplenishmentFilterDto(
        Long id,
        Long clientId,
        Long cardNo,
        BigDecimal amount,
        LocalDateTime timeOfReplenishment,
        Status status
) {
}
