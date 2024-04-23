package com.pers.dto.filter;


import com.pers.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ClientFilterDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        BigDecimal balance,
        String phone,
        Status status) {
}
