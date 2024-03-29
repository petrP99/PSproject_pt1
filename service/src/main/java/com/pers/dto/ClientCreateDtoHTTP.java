package com.pers.dto;

import com.pers.entity.Status;
import com.pers.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientCreateDtoHTTP {
    Long id;
    User user;
    Status status;
    String firstName;
    String lastName;
    BigDecimal balance;
    LocalDateTime createdTime;
}
