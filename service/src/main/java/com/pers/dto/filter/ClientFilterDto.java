package com.pers.dto.filter;


import com.pers.entity.Status;

import java.math.BigDecimal;

public record ClientFilterDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        BigDecimal balance,
        String phone,
        Status status) {
}
