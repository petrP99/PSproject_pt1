package com.pers.dto;

import com.pers.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientReadDtoHTTP {
    Long id;
    UserReadDto user;
    Status status;
    String firstName;
    String lastName;
    BigDecimal balance;
    LocalDateTime createdTime;
}
