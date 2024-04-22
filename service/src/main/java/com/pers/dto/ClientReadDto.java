package com.pers.dto;

import com.pers.entity.Status;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class ClientReadDto {

    Long id;
    UserReadDto userId;
    BigDecimal balance;
    String firstName;
    String lastName;
    String phone;
    Status status;
    LocalDateTime createdTime;
}