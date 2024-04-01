package com.pers.service;

import com.pers.dto.*;
import com.pers.integration.repository.BaseIntegrationIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.pers.dto.UserCreateDto.Fields.login;
import static com.pers.dto.UserCreateDto.Fields.password;
import static com.pers.dto.UserCreateDto.Fields.role;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends BaseIntegrationIT {

    private final MockMvc mockMvc;

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(login, "1@mail.ru")
                        .param(password, "123")
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
                .andExpect(model().attribute("users", hasSize(6)));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/1/update")
                        .param(login, "user1@mail.ru")
                        .param(password, "456"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/*")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/5/delete"))
                .andExpectAll(
                        status().is3xxRedirection()
//                        redirectedUrlPattern("/users")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/3"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"));
    }

}