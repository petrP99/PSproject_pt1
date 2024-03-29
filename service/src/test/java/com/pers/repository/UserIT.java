package com.pers.repository;

import com.pers.entity.Role;
import com.pers.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class UserIT extends BaseTestRepositoryIT {

    UserRepository userRepository;

    @BeforeEach
    void prepare() {
        entityManager.getTransaction().begin();
        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    void createUser() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var actualResult = userRepository.findAll();

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult).hasSize(1);
    }

    @Test
    void findById() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var actualResult = userRepository.findAllById(user.getId());

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult.get(0)).isEqualTo(user);
    }

    @Test
    void findAllByRole() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        var actualResult = userRepository.findAllByRole(Role.ADMIN);

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult.get(0)).isEqualTo(user);
    }
}