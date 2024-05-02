package com.pers.http.config;

import com.pers.dto.ClientReadDto;
import com.pers.service.ClientService;
import com.pers.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final ClientService clientService;

    public CustomAuthenticationSuccessHandler(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        var userName = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication1 -> (UserDetails) authentication1.getPrincipal())
                .map(UserDetails::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user"));

        var client = clientService.findByUserName(userName);
        var userId = userService.findIdByLogin(userName).orElseThrow().getId();
        if (client.isPresent()) {
            var clientId = client.map(ClientReadDto::getId).orElseThrow();
            response.sendRedirect("/clients/home/" + clientId);
        } else {
            response.sendRedirect("/clients/registration/" + userId);
        }
    }
}