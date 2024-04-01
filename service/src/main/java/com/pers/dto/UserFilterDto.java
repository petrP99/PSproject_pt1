package com.pers.dto;

import com.pers.entity.Role;

public record UserFilterDto(String login, Role role) {
}
