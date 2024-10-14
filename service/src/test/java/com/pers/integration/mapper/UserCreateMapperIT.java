package com.pers.integration.mapper;

import com.pers.dto.UserCreateDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.UserCreateMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserCreateMapperIT extends BaseIntegrationIT {

    private final PasswordEncoder passwordEncoder;
    private UserCreateMapper userCreateMapper;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void init() {
        userCreateDto = new UserCreateDto(("test@mail.ru"), "{noop}123", Role.USER);
    }

    @Test
    void copy() {
        var result = userCreateMapper.copy(userCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getRole().name()).isEqualTo("USER");
    }

    @Test
    void updateMap() {
        var user = User.builder()
                .login("123@mail.ru")
                .password("{noop}123")
                .role(Role.USER)
                .build();
        entityManager.persist(user);
        var result = userCreateMapper.map(userCreateDto, user);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("test@mail.ru");
    }

    @Test
    void mapFrom() {
        var result = userCreateMapper.mapFrom(userCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("test@mail.ru");
    }
}















