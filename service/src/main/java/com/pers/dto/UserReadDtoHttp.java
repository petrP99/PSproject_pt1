package com.pers.dto;

import com.pers.entity.Role;
import lombok.Value;

@Value
public class UserReadDtoHttp {
    Long id;
    String login;
    String password;
    Role role;
}
