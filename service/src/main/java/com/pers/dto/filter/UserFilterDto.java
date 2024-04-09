package com.pers.dto.filter;

import com.pers.entity.Role;
import lombok.Value;
import org.springframework.stereotype.Component;

public record UserFilterDto(String login,
                            Role role) {
}
