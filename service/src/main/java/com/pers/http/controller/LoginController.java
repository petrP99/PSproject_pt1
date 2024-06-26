package com.pers.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping()
    public String homePage() {
        return "client/home";
    }

    @GetMapping("/admin/main")
    public String main() {
        return "admin/main";
    }
}