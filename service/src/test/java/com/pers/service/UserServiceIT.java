package com.pers.service;

import com.pers.dto.UserReadDto;
import com.pers.entity.User;
import com.pers.integration.repository.BaseIntegrationIT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pers.entity.Role.USER;

@RequiredArgsConstructor
class UserServiceIT extends BaseIntegrationIT {

    private final UserService userService;
    private final EntityManager entityManager;

    @Test
    void findAll() {
        var user = User.builder()
                .login("user123")
                .password("123")
                .role(USER)
                .build();

        entityManager.persist(user);


        List<UserReadDto> result = userService.findAll();
        Assertions.assertThat(result).hasSize(1);

    }

}