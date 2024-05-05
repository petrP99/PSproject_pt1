package com.pers.integration.mapper;

import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class UserReadMapperIT extends BaseIntegrationIT {

    private final UserReadMapper userReadMapper;

    @Test
    void mapFrom() {
        var user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();
        entityManager.persist(user);

        var result = userReadMapper.mapFrom(user);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("test@mail.ru");
    }
}


















