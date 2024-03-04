package com.pers.dao;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.Transfer;
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

public class TransferIT {
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
        TransferRepository transferRepository = new TransferRepository(session);

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

        var user2 = User.builder()
                .login("userEx2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .firstName("Petr")
                .lastName("Petrov")
                .balance(new BigDecimal(7400))
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .cardNo(generateNumber())
                .balance(new BigDecimal(85241))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var transfer = Transfer.builder()
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(new BigDecimal(368))
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        clientRepository.save(client);
        clientRepository.save(client2);
        cardRepository.save(card);
        cardRepository.save(card2);
        transferRepository.save(transfer);

        var result = transferRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void findByCardNoTo() {
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);
        CardRepository cardRepository = new CardRepository(session);
        TransferRepository transferRepository = new TransferRepository(session);

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

        var user2 = User.builder()
                .login("userEx2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .firstName("Petr")
                .lastName("Petrov")
                .balance(new BigDecimal(7400))
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .cardNo(generateNumber())
                .balance(new BigDecimal(85241))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var transfer = Transfer.builder()
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(new BigDecimal(368))
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        clientRepository.save(client);
        clientRepository.save(client2);
        cardRepository.save(card);
        cardRepository.save(card2);
        transferRepository.save(transfer);

        var result = transferRepository.findByCardNoTo(card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
