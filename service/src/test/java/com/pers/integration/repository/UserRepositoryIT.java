package com.pers.integration.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
public class UserRepositoryIT extends BaseIntegrationIT {

    private final UserRepository userRepository;

    @Test
    void createUser() {
        User user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var result = userRepository.findByLogin("test@mail.ru");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    void updateUser() {
        User user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        var result = userRepository.findByLogin("test@mail.ru");

        assertThat(result).isPresent();
        assertThat(result.get().getRole()).isEqualTo(Role.ADMIN);

    }

    @Test
    void deleteUser() {
        User user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);
        userRepository.delete(user);

        var result = userRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findByFilter() {
        User user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .login("test2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        var filter = UserFilterDto.builder()
                .login("test")
                .build();
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        var result = userRepository.findAllByFilter(filter, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
    }

}
