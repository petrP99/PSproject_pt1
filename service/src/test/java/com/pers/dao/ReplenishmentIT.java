package com.pers.dao;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Replenishment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplenishmentIT {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void clean() {
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    void createTransfer() {
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);
        CardRepository cardRepository = new CardRepository(session);
        ReplenishmentRepository replenishmentRepository = new ReplenishmentRepository(session);

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
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);
        CardRepository cardRepository = new CardRepository(session);
        ReplenishmentRepository replenishmentRepository = new ReplenishmentRepository(session);

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
