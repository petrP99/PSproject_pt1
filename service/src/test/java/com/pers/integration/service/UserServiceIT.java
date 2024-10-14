package com.pers.integration.service;

import com.pers.dto.UserCreateDto;
import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static com.pers.entity.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class UserServiceIT extends BaseIntegrationIT {

    private final UserService userService;

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
                    assertThat(user.getLogin()).isEqualTo("user99@mail.ru");
                }));
    }

    @Test
    void findAllByFilter() {
        var userFilterDto = new UserFilterDto("@", USER);

        var result = userService.findAll(userFilterDto, Pageable.ofSize(1));

        assertThat(result).hasSize(1);
    }
}


