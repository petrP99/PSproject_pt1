package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Replenishment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplenishmentIT extends BaseTestRepositoryIT {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private CardRepository cardRepository;
    private ReplenishmentRepository replenishmentRepository;

    @BeforeEach
    void openSession() {
        entityManager.getTransaction().begin();
        userRepository = context.getBean(UserRepository.class);
        clientRepository = context.getBean(ClientRepository.class);
        cardRepository = context.getBean(CardRepository.class);
        replenishmentRepository = context.getBean(ReplenishmentRepository.class);
    }

    @Test
    void createTransfer() {
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
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var replenishment = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(new BigDecimal(52))
                .timeOfReplenishment(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        var replenishment2 = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(new BigDecimal(1457))
                .timeOfReplenishment(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        replenishmentRepository.save(replenishment);
        replenishmentRepository.save(replenishment2);

        var result = replenishmentRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void findByClientIdAndCardNo() {
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
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(1))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var replenishment = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(new BigDecimal(52))
                .timeOfReplenishment(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        var replenishment2 = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card2)
                .amount(new BigDecimal(1457))
                .timeOfReplenishment(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        cardRepository.save(card2);
        replenishmentRepository.save(replenishment);
        replenishmentRepository.save(replenishment2);

        var result = replenishmentRepository.findByClientIdAndCardNo(client.getId(), card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
