package com.pers.dto;

import com.pers.entity.Status;
import com.pers.validation.TransferInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

@TransferInfo
public record TransferCreateDto(
        Long clientId,
        Long cardIdFrom,
        Long cardIdTo,

        @Positive
        BigDecimal amount,
        Instant timeOfTransfer,
        String recipient,
        String message,
        Status status
) {
}
