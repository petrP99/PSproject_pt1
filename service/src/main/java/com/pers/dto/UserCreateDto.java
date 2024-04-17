package com.pers.dto;

import com.pers.entity.Role;
import com.pers.validation.UserInfo;
import com.pers.validation.group.CreateAction;
import com.pers.validation.group.UpdateAction;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
@UserInfo(groups = UpdateAction.class)
public class UserCreateDto {

    @Email
    String login;

    @NotBlank(groups = CreateAction.class)
    @Size(min = 3, max = 20, message = "password must not be at least 3 character")
    String rawPassword;

    Role role;
}
