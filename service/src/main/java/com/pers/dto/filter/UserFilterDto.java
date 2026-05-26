package com.pers.dto.filter;

import com.pers.enums.Role;
import lombok.Builder;

@Builder
public record UserFilterDto(String login,
                            Role role) {
}
