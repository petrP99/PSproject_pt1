package com.pers.integration.mapper;

import com.pers.dto.ClientUpdateBalanceDto;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.ClientUpdateBalanceMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ClientUpdateBalanceMapperIT extends BaseIntegrationIT {

    private final ClientUpdateBalanceMapper clientCreateMapper;
    private User user;
    private Client client;
    private ClientUpdateBalanceDto clientCreateDto;

    @BeforeEach
    void prepare() {
        user = User.builder()
                .login("test@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();
        entityManager.persist(user);

        client = Client.builder()
                .user(user)
                .balance(new BigDecimal(0))
                .firstName("petr")
                .lastName("petrov")
                .phone("89638521478")
                .createdTime(Instant.now())
                .status(Status.ACTIVE)
                .build();
        entityManager.persist(client);

        clientCreateDto = new ClientUpdateBalanceDto(client.getId(), user.getId(),
                new BigDecimal(10),
                "ivan", "ivanov",
                "89774115588", Status.BLOCKED, Instant.now());
    }

    @Test
    void mapFrom() {
        var result = clientCreateMapper.mapFrom(clientCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("ivan");
    }

    @Test
    void map() {
        var result = clientCreateMapper.map(clientCreateDto, client);

        assertThat(result).isNotNull();
        assertThat(result.getPhone()).isEqualTo("89638521478");
    }
}


















