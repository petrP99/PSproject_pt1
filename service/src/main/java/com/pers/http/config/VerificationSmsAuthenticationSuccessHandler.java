package com.pers.http.config;

import com.pers.service.GenerateAndCheckCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class VerificationSmsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final GenerateAndCheckCodeService generateAndCheckCodeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (request.getSession().getAttribute("smsCode") != null) {
            if (generateAndCheckCodeService.checkCodeByUser((Integer) request.getSession().getAttribute("smsCode"), 1000)) {
                response.sendRedirect("/clients/home");
            } else {
                response.sendRedirect("/login/success");
            }
        }
    }
}

