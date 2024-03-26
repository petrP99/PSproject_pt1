package com.pers.dto.filter;

import com.pers.entity.Role;

public record UserFilterDto(String login,
                            Role role) {
}
