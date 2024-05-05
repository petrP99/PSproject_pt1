package com.pers.dto.filter;

import com.pers.entity.Role;
import lombok.Builder;

@Builder
public record UserFilterDto(String login,
                            Role role) {
}
