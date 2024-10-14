package com.pers.integration.mapper;

import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.ClientReadMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ClientReadMapperIT extends BaseIntegrationIT {

    private final ClientReadMapper clientReadMapper;
    private User user;
    private Client client;

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
    }

    @Test
    void mapFrom() {
        var result = clientReadMapper.mapFrom(client);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("petr");
    }
}


















