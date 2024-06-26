package com.pers.http.controller;

import com.pers.dto.CardCreateDto;
import com.pers.dto.CardReadDto;
import com.pers.dto.filter.CardFilterDto;
import com.pers.dto.filter.PageResponse;
import com.pers.service.CardService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping("/create")
    public String create(@Validated CardCreateDto card,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpSession session) {
        session.getAttribute("clientId");
        cardService.create(card);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("card", card);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/cards";
        }
        return "card/message";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id) {
        var card = cardService.findById(id).orElseThrow();
        return cardService.updateStatusToBlocked(card)
                .map(it -> "redirect:/cards/cards")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!cardService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/cards";
    }

    @GetMapping("/cards")
    public String findByClientId(@Validated Model model, HttpSession session) {
        cardService.checkCardExpire();
        session.getAttribute("clientId");
        var clientId = (Long) session.getAttribute("clientId");
        var myCards = cardService.findByClientId(clientId);
        model.addAttribute("cards", myCards);
        return "card/cards";
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAll(Model model, CardFilterDto filter, Pageable pageable) {
        Page<CardReadDto> page = cardService.findAllByFilter(filter, pageable);
        model.addAttribute("cards", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "card/cards";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return cardService.findById(id)
                .map(card -> {
                    model.addAttribute("card", card);
                    return "card/card";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
