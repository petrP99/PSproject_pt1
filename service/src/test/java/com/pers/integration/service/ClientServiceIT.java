package com.pers.integration.service;

import com.pers.dto.ClientCreateDto;
import com.pers.dto.UserCreateDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.service.ClientService;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.pers.entity.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class ClientServiceIT extends BaseIntegrationIT {

    private final ClientService clientService;
    private final EntityManager entityManager;
    private User user;
    private Client client;
    private Client client2;
    private ClientCreateDto clientCreateDto;

    @BeforeEach
    void prepare() {
        user = User.builder()
                .login("user10@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var user2 = User.builder()
                .login("admin@mail.ru")
                .password("123")
                .role(Role.ADMIN)
                .build();

        client = Client.builder()
                .user(user)
                .balance(new BigDecimal(0))
                .firstName("Petr")
                .lastName("Petrov")
                .phone("89632587854")
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        client2 = Client.builder()
                .user(user2)
                .balance(new BigDecimal(100))
                .firstName("Sidr")
                .lastName("Sidorov")
                .phone("89638887854")
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(client);
        entityManager.persist(client2);

        clientCreateDto = new ClientCreateDto(user2.getId(), client.getBalance(), client.getFirstName(), "Ivanov",
                client.getPhone(), client.getStatus(), client.getCreatedTime());

    }

    @Test
    void findAll() {
        var result = clientService.findAll(null);

        assertThat(result).hasSize(1);
    }

    @Test
    void findById() {
        var result = clientService.findById(client.getId());

        assertThat(result).isPresent();
    }

    @Test
    void create() {
        var result = clientService.create(clientCreateDto);

        assertThat(result.firstName()).isEqualTo("Petr");
    }

    @Test
    void update() {
        var result = clientService.update(user.getId(), clientCreateDto);

        assertThat(result).isPresent();
        result.ifPresent(client ->
                assertAll(() -> {
                    assertThat(client.lastName()).isEqualTo("Ivanov");
                }));
    }

    @Test
    void findAllByFilter() {
        ClientFilterDto filterDto = new ClientFilterDto(client2.getStatus(), null,
                null, null, null, null);

        var result = clientService.findAll(filterDto);

        assertThat(result).hasSize(1);
    }
}


