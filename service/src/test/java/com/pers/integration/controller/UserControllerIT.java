package com.pers.integration.controller;

import com.pers.dto.UserCreateDto;
import static com.pers.dto.UserCreateDto.Fields.rawPassword;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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
//
//    @BeforeEach
//    void init() {
//        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER, Role.SUPER_ADMIN);
//        user = new User(1l, "user1@mail.ru", "test", Role.USER);
//
//
//        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, user.getPassword(), roles);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(testingAuthenticationToken);
//        SecurityContextHolder.setContext(securityContext);
//    }


    @Test
    void create() throws Exception {

        var userCreateDto = new UserCreateDto("user1@mail.ru", "test", Role.USER);

        mockMvc.perform(post("/users")
                        .param(login, userCreateDto.getLogin())
                        .param(rawPassword, userCreateDto.getRawPassword())
                        .param(role, userCreateDto.getRole().name())
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login"),
                        model().hasNoErrors());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/{id}/update", user.getId())
                        .param(login, "user10@mail.ru"))
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
