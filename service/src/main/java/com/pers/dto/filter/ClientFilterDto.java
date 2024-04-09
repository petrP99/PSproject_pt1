package com.pers.dto.filter;


import com.pers.entity.*;

import java.math.*;
import java.time.*;

public record ClientFilterDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        BigDecimal balance,
        String phone,
        Status status) {
}
