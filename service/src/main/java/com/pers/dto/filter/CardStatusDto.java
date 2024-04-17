package com.pers.dto.filter;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardStatusDto(Long id,
                            Long clientId,
                            BigDecimal balance,
                            LocalDate createdDate,
                            LocalDate expireDate,
                            Status status) {
}
