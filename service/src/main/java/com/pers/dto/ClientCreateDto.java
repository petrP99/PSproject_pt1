package com.pers.dto;

import com.pers.entity.Status;
import com.pers.validation.ClientInfo;
import jakarta.validation.constraints.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ClientInfo
@FieldNameConstants
public record ClientCreateDto(Long userId,
                              @PositiveOrZero
                              BigDecimal balance,
                              @NotBlank
                              String firstName,
                              @NotBlank
                              String lastName,
                              @NotBlank
                                      @Size(min = 11, max = 11)
                              String phone,
                              Status status,
//                              @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
                              LocalDateTime createdTime) {
}
