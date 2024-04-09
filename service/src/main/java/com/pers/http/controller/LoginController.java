package com.pers.http.controller;

import com.pers.service.ClientService;
import com.pers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final ClientService clientService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login/check")
    public String checkExistClient() {
        var userName = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user"));

        var client = clientService.findByUserName(userName);
        var userId = userService.findIdByLogin(userName);

        if (client.isPresent()) {
            return "redirect:/clients/home";
        } else {
            return "redirect:/clients/registration/" + userId;
        }
    }
}