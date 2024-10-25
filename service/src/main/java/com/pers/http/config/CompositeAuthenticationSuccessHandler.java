package com.pers.http.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CompositeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler[] successHandlers;

    public CompositeAuthenticationSuccessHandler(AuthenticationSuccessHandler... successHandlers) {
        this.successHandlers = successHandlers;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        for (AuthenticationSuccessHandler successHandler : successHandlers) {
            successHandler.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
