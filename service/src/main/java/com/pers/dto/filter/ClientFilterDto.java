package com.pers.dto.filter;


import com.pers.entity.*;

import java.math.*;
import java.time.*;

public record ClientFilterDto(
        Status status,
        String firstName,
        String lastName,
        String phone,
        BigDecimal balance,
        LocalDateTime createdTime) {
}
