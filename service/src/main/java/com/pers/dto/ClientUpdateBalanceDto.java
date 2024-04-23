package com.pers.dto;

import com.pers.entity.Status;
import com.pers.validation.ClientInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;

@ClientInfo
@FieldNameConstants
public record ClientUpdateBalanceDto(
        Long id,
        Long userId,

        @PositiveOrZero
        BigDecimal balance,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Size(min = 11, max = 11)
        String phone,
        Status status,
        Instant createdTime) {
}
