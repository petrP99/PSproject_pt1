package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CardIT extends BaseTestRepositoryIT {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private CardRepository cardRepository;

    @BeforeEach
    void openSession() {
        entityManager.getTransaction().begin();
        userRepository = context.getBean(UserRepository.class);
        clientRepository = context.getBean(ClientRepository.class);
        cardRepository = context.getBean(CardRepository.class);
    }

    @Test
    void createCard() {
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

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);

        var result = cardRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void findByClientId() {
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

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);

        var result = cardRepository.findByClientId(client.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void findByStatus() {
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

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.BLOCKED)
                .build();

        var card2 = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(888))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.BLOCKED)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        cardRepository.save(card2);

        var result = cardRepository.findByStatus(Status.BLOCKED);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }
}
