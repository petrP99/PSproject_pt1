package com.pers.dto;

import com.pers.entity.Role;

public record UserReadDto(Long id,
                          String login,
                          String password,
                          Role role) {
}
