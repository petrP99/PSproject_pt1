package com.pers.integration.repository;

import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.repository.ClientRepository;
import com.pers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class ClientIT extends BaseIntegrationIT {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Test
    void createClient() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .firstName("Ivan")
                .lastName("Ivanov")
                .balance(new BigDecimal(10000))
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        var result = clientRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void deleteClient() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .firstName("Ivan")
                .lastName("Ivanov")
                .balance(new BigDecimal(10000))
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        clientRepository.delete(client);
        var result = clientRepository.findAll();

        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    void findByUserId() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .firstName("Ivan")
                .lastName("Ivanov")
                .balance(new BigDecimal(10000))
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        var result = clientRepository.findByUserId(user.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
