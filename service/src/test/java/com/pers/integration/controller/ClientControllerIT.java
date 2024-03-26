package com.pers.integration.controller;

import com.pers.dto.ClientCreateDto;
import com.pers.entity.Client;
import com.pers.entity.QClient;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.pers.dto.UserCreateDto.Fields.login;
import static com.pers.dto.UserCreateDto.Fields.password;
import static com.pers.dto.UserCreateDto.Fields.role;
import static com.pers.entity.Client_.firstName;
import static com.pers.entity.Client_.lastName;
import static com.pers.entity.QClient.client;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class ClientControllerIT extends BaseIntegrationIT {

    private final MockMvc mockMvc;
    private final EntityManager entityManager;
    private User user;
    private Client client;
    private Client client2;
    private ClientCreateDto clientCreateDto;

    @BeforeEach
    void prepare() {
        user = User.builder()
                .login("user10@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        client = Client.builder()
                .user(user)
                .balance(new BigDecimal(0))
                .firstName("Petr")
                .lastName("Petrov")
                .phone("89632587854")
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        entityManager.persist(user);
        entityManager.persist(client);
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/client")
                        .param(client.getFirstName(), "Petr")
                        .param(client.getLastName(), "Petrov")
                        .param(client.getPhone(), "89632587854")
                        .param(client.getStatus().name(), "ACTIVE"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/client/home")
                );
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/client"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("client", hasSize(1)));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/client/{id}/update", client.getId())
                        .param(client.getFirstName(), "Petr")
                        .param(client.getPhone(), "89632587854"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/client/{id}")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", client.getId()))
                .andExpectAll(
                        status().is3xxRedirection()
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/client", client.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("client"));
    }
}
