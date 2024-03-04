package com.pers.dao;

import com.pers.entity.Client;
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
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientIT {
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
    void createClient() {
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);

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
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);

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
        clientRepository.delete(client.getId());
        var result = clientRepository.findAll();

        assertThat(result).isEmpty();
        assertThat(result).hasSize(0);
    }

    @Test
    void findByUserId() {
        UserRepository userRepository = new UserRepository(session);
        ClientRepository clientRepository = new ClientRepository(session);

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
