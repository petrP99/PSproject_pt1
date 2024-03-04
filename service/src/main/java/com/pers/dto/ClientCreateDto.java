package com.pers.dto;

import com.pers.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ClientCreateDto(Long userId,
                              BigDecimal balance,
                              String firstName,
                              String lastName,
                              Status status,
                              LocalDateTime createdTime) {
}
