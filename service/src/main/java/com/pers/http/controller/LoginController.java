package com.pers.http.controller;

import com.pers.dto.ClientReadDto;
import com.pers.dto.SmsCodeDto;
import com.pers.dto.UserCreateDto;
import com.pers.service.ClientService;
import com.pers.service.GenerateAndCheckCodeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final GenerateAndCheckCodeService generateAndCheckCodeService;
    private final ClientService clientService;

    @GetMapping("/login")
    public String loginPage(@Validated @ModelAttribute UserCreateDto user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        return "user/login";
    }

    @PostMapping("/login/success")
    public String checkVerificationCode(@ModelAttribute("codeDto") SmsCodeDto codeDto, HttpSession session) {
        var code = generateAndCheckCodeService.generateCode();
        session.setAttribute("smsCode", code);
        return "redirect:/login-check-sms";
    }

    @GetMapping("/login-check-sms")
    public String loginCodePage(HttpSession session, SmsCodeDto codeDto) {
        Optional<ClientReadDto> clientId = clientService.findByUserName((String) session.getAttribute("userName"));
        session.setAttribute("clientId", clientId.get().getId());
        return generateAndCheckCodeService.checkCodeByUser((int) session.getAttribute("smsCode"), codeDto.getValue())
                ? String.format("redirect:/clients/home/{%s}", session.getAttribute("clientId"))
                : "user/login-check-sms";
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