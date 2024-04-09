package com.pers.dto;

import com.pers.entity.Status;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardCreateDto(Long clientId,
                            @PositiveOrZero
                            BigDecimal balance,
                            LocalDate createdDate,
                            LocalDate expireDate,
                            Status status) {
}
