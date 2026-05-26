package com.pers.dto;

import com.pers.enums.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;
    String login;
    String password;
    Role role;
}
