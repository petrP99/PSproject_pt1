package com.pers.http.greeting;

import com.pers.dto.LoginDto;
import com.pers.service.ClientService;
import com.pers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/users")
    public String login(Model model, @ModelAttribute("login") LoginDto loginDto) {
        return "user/login";

    }

}
