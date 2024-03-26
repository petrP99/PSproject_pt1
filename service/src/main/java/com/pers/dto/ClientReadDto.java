package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ClientReadDto(Long id,
                            UserReadDto userId,
                            BigDecimal balance,
                            String firstName,
                            String lastName,
                            String phone,
                            Status status,
                            LocalDateTime createdTime) {
}
