package com.pers.integration.repository;

import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UserIT extends BaseIntegrationIT {

    private final UserRepository userRepository;

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