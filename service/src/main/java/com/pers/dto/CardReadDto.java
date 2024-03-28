package com.pers.dto;

import com.pers.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardReadDto(Long id,
                          Long clientId,
                          Long cardNo,
                          BigDecimal balance,
                          LocalDate createdDate,
                          LocalDate expireDate,
                          Status status) {
}
