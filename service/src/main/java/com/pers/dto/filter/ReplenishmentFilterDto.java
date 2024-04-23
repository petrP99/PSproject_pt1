package com.pers.dto.filter;

import com.pers.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ReplenishmentFilterDto(
        Long id,
        Long clientId,
        Long cardNo,
        BigDecimal amount,
        LocalDateTime timeOfReplenishment,
        Status status
) {
}
