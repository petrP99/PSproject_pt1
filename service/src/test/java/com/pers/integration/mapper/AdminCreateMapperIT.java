package com.pers.integration.mapper;

import com.pers.dto.UserCreateDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.AdminCreateMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class AdminCreateMapperIT extends BaseIntegrationIT {

    private AdminCreateMapper adminCreateMapper;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void init() {
        userCreateDto = new UserCreateDto(("admin@mail.ru"), "{noop}123", Role.USER);
    }

    @Test
    void copy() {
        var result = adminCreateMapper.copy(userCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getRole().name()).isEqualTo("ADMIN");
    }

    @Test
    void updateMap() {
        var user = User.builder()
                .login("123@mail.ru")
                .password("{noop}123")
                .role(Role.USER)
                .build();
        entityManager.persist(user);
        var result = adminCreateMapper.map(userCreateDto, user);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("test@mail.ru");
    }

    @Test
    void mapFrom() {
        var result = adminCreateMapper.mapFrom(userCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("test@mail.ru");
    }
}















