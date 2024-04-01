package com.pers.dto;

import com.pers.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateDto {
    String login;
    String password;
    Role role;
}
