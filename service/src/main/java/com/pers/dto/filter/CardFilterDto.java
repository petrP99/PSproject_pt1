package com.pers.dto.filter;


import com.pers.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CardFilterDto(
        Long id,
        Long clientId,
        BigDecimal balance,
        LocalDate expireDate,
        Status status) {
}
