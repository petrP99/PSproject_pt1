package com.pers.integration.mapper;

import com.pers.dto.PaymentCreateDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.mapper.PaymentCreateMapper;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@RequiredArgsConstructor
class PaymentCreateMapperIT extends BaseIntegrationIT {

    private final PaymentCreateMapper paymentCreateMapper;
    private User user;
    private Client client;
    private Card card;
    private PaymentCreateDto paymentCreateDto;

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

        card = Card.builder()
                .client(client)
                .balance(new BigDecimal(100))
                .createdDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(3))
                .status(Status.ACTIVE)
                .build();
        entityManager.persist(card);

        paymentCreateDto = new PaymentCreateDto("anything", new BigDecimal(100),
                client.getId(), card.getId(), Instant.now(), Status.SUCCESS);
    }

    @Test
    void mapFrom() {
        var result = paymentCreateMapper.mapFrom(paymentCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(100));
    }
}
