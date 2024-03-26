package com.pers.dto;

import com.pers.entity.Role;
import com.pers.validation.*;
import jakarta.validation.constraints.*;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.*;

@Value
@FieldNameConstants
@UserInfo
public class UserCreateDto {

    @Email
    String login;

    @Size(min = 3, max = 20, message = "password must not be at least 3 character")
    String password;
    Role role;
}
