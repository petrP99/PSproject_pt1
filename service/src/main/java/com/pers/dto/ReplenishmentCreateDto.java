package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReplenishmentCreateDto(
        Long clientId,
        Long cardNo,
        BigDecimal amount,
        LocalDateTime timeOfReplenishment,
        Status status

) {
}
