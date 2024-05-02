package com.pers.dto;

import com.pers.entity.Status;
import com.pers.validation.ClientInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@ClientInfo
@FieldNameConstants
public record ClientCreateDto(Long userId,
                              @PositiveOrZero
                              BigDecimal balance,

                              @NotBlank
                              @Pattern(regexp = "[а-яА-яa-zA-Z]+")
                              String firstName,

                              @NotBlank
                              @Pattern(regexp = "[а-яА-яa-zA-Z]+")
                              String lastName,

                              @NotBlank
                              @Size(min = 11, max = 11)
                              String phone,
                              Status status,
                              Instant createdTime) {
}
