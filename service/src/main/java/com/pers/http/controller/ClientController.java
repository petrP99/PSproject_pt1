package com.pers.http.controller;

import com.pers.dto.ClientCreateDto;
import com.pers.dto.ClientReadDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.dto.filter.PageResponse;
import com.pers.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/registration/{id}")
    public String registration(@Validated @PathVariable(value = "id") Long userId,
                               Model model,
                               @ModelAttribute("client") ClientCreateDto client,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("client", client);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/clients/registration/" + client.userId();
        }
        model.addAttribute("userId", client.userId());
        model.addAttribute("client", client);
        return "client/registration";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute("client") ClientCreateDto client,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        if (clientService.findByUserId(client.userId()).isPresent()) {
            return "redirect:/login";
        }
        var newClient = clientService.create(client);
        request.getSession().setAttribute("clientId", newClient.getId());
        if (clientService.findByPhone(client.phone()).isPresent() || bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("client", client);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/clients/registration/" + client.userId();
        }
        return "redirect:/clients/home/" + newClient.getId();
    }

    @GetMapping("/home/{id}")
    public String homePage(@PathVariable("id") Long id, HttpSession session) {
        session.setAttribute("clientId", id);
        session.setAttribute("balance", clientService.findById(id).orElseThrow().getBalance());
        return "client/home";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated ClientCreateDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/clients/home/" + id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/update-by-admin")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String updateByAdmin(@PathVariable("id") Long id, @ModelAttribute @Validated ClientCreateDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/clients")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!clientService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/clients";
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAll(Model model, ClientFilterDto filter, Pageable pageable) {
        Page<ClientReadDto> page = clientService.findAll(filter, pageable);
        model.addAttribute("clients", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "client/clients";
    }

    @GetMapping("/client")
    @PreAuthorize("hasAnyAuthority('USER', 'SUPER_ADMIN')")
    public String findById(Model model, HttpSession session) {
        return clientService.findById((Long) session.getAttribute("clientId"))
                .map(client -> {
                    model.addAttribute("client", client);
                    return "client/client-update";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

}
