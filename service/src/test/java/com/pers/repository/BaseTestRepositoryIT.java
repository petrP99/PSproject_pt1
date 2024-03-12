package com.pers.repository;

import com.pers.config.ApplicationConfigurationIT;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BaseTestRepositoryIT {

    protected static AnnotationConfigApplicationContext context;
    protected static SessionFactory sessionFactory;
    protected static EntityManager entityManager;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationIT.class);
        sessionFactory = context.getBean(SessionFactory.class);
        entityManager = context.getBean(EntityManager.class);
    }

    @AfterAll
    static void closeFactory() {
        sessionFactory.close();
        context.close();
    }

    @AfterEach
    void closeEntityManager() {
        entityManager.getTransaction().rollback();
    }
}
