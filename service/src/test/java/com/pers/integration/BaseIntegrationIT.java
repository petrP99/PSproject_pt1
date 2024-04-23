package com.pers.integration;

import com.pers.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.*;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
//@WithMockUser(username = "user10@mail.ru", password = "test", authorities = {"USER, ADMIN, SUPER_ADMIN"})
public abstract class BaseIntegrationIT {

    @Autowired
    protected EntityManager entityManager;
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void startContainer() {
        postgres.start();
    }
}
