package com.pers.dao;

import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class UserIT {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory();
//        Session proxySession = HibernateUtil.buildSessionProxy(sessionFactory);
//        userRepository = new UserRepository(proxySession);
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
    void createUser() {
        UserRepository userRepository = new UserRepository(session);
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var actualResult = userRepository.findAll();

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult).hasSize(1);
    }

    @Test
    void findById() {
        UserRepository userRepository = new UserRepository(session);
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var actualResult = userRepository.findAllById(user.getId());

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult.get(0)).isEqualTo(user);
    }

    @Test
    void findAllByRole() {
        UserRepository userRepository = new UserRepository(session);
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        var actualResult = userRepository.findAllByRole(Role.ADMIN);

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult.get(0)).isEqualTo(user);
    }
}