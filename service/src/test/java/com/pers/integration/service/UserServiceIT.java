package com.pers.integration.service;

import com.pers.dto.*;
import com.pers.dto.filter.*;
import com.pers.entity.*;
import com.pers.integration.BaseIntegrationIT;
import com.pers.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

import static com.pers.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class UserServiceIT extends BaseIntegrationIT {

    private final UserService userService;
    private final EntityManager entityManager;

    private User user;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void prepare() {
        user = User.builder()
                .login("user10@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .login("admin@mail.ru")
                .password("123")
                .role(Role.ADMIN)
                .build();

        entityManager.persist(user);
        entityManager.persist(user2);

        userCreateDto = new UserCreateDto(
                "user99@mail.ru",
                "456",
                USER
        );
    }

    @Test
    void findAll() {
        var result = userService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById() {
        var result = userService.findById(user.getId());

        assertThat(result).isPresent();
    }

    @Test
    void create() {
        var result = userService.create(userCreateDto);

        assertThat(result.getLogin()).isEqualTo("user99@mail.ru");
    }

    @Test
    void update() {
        var result = userService.update(user.getId(), userCreateDto);

        assertThat(result).isPresent();
        result.ifPresent(user ->
                assertAll(() -> {
                    assertThat(user.getPassword()).isEqualTo("456");
                }));
    }

    @Test
    void findAllByFilter() {
        UserFilterDto userFilterDto = new UserFilterDto("@", null);

        var result = userService.findAll(userFilterDto, null);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }
}


