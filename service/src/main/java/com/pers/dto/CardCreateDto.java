package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardCreateDto(Long clientId,
                            Integer cardNo,
                            BigDecimal balance,
                            LocalDate createdDate,
                            LocalDate expireDate,
                            Status status) {
}
