package com.pers.dao;

import com.pers.entity.User;
import com.pers.util.HibernateUtil;
import com.pers.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoTest {

    private final UserDao userDao = UserDao.getINSTANCE();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    TestDataImporter.

    @Test
    void findAll() {
    }

    @Test
    void findByLogin() {

        List<User> result = userDao.findByLogin(session, "user@mail.com");

    }

    @Test
    void findAllOrderBy() {
    }
}