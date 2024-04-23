package com.pers.dto.filter;

import com.pers.entity.Role;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Builder
public record UserFilterDto(String login,
                            Role role) {
}
