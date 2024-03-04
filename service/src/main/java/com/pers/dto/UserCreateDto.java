package com.pers.dto;

import com.pers.entity.Role;

public record UserCreateDto(String login,
                            String password,
                            Role role) {
}
