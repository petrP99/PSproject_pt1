package com.pers.integration.controller;

import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.pers.dto.UserCreateDto.Fields.login;
import static com.pers.dto.UserCreateDto.Fields.role;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends BaseIntegrationIT {

    private final MockMvc mockMvc;
    private final EntityManager entityManager;
    private User user;

    @BeforeEach
    void prepare() {
        user = User.builder()
                .login("user10@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();
        entityManager.persist(user);
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(login, "1@mail.ru")
                        .param(role, "USER"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                        );
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(1)));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/{id}/update", user.getId())
                        .param(login, "user1@mail.ru"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/*")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", user.getId()))
                .andExpectAll(
                        status().is3xxRedirection()
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/user"),
                        model().attributeExists("user"));
    }
}
