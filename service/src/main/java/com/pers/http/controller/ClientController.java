package com.pers.http.controller;

import com.pers.dto.ClientCreateDto;
import com.pers.dto.UserCreateDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
@SessionAttributes({"clientId"})
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/registration/{id}")
    public String registration(@Validated @PathVariable(value = "id") Long userId, Model model,
                               @ModelAttribute("client") ClientCreateDto client) {
        model.addAttribute("userId", client.userId());
        model.addAttribute("client", client);
        return "client/registration";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute("client") ClientCreateDto client, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (clientService.findByUserId(client.userId()).isPresent()) {
            return "redirect:/users/registration";
        }
        var newClient = clientService.create(client);
        request.getSession().setAttribute("clientId", newClient.id());
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("client", client);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/client/registration/" + client.userId();
        }
        return "redirect:/client/home";
    }

    @GetMapping("/home")
    public String any() {
        return "client/home";
    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated ClientCreateDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/client/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!clientService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/client";
    }

    @GetMapping()
    public String findAll(Model model, ClientFilterDto filter, Pageable pageable) {
        model.addAttribute("client", clientService.findAll(filter, pageable));
        return "client/client";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    return "client/client";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
