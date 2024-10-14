package com.pers.http.controller;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.dto.ReplenishmentReadDto;
import com.pers.dto.filter.PageResponse;
import com.pers.dto.filter.ReplenishmentFilterDto;
import com.pers.service.CardService;
import com.pers.service.ReplenishmentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/replenishments")
public class ReplenishmentController {

    private final ReplenishmentService replenishmentService;
    private final CardService cardService;

    @GetMapping("/replenishment")
    public String services(@Validated @ModelAttribute("replenishment") ReplenishmentCreateDto replenishment, HttpSession session, Model model) {
        var cards = cardService.findActiveCardsByClientId((Long) session.getAttribute("clientId"));
        model.addAttribute("replenishment", replenishment);
        model.addAttribute("cards", cards);
        return "replenishment/replenishment";
    }

    @GetMapping("/replenishment/{id}")
    public String cardReplenishment(@Validated @ModelAttribute("replenishment") ReplenishmentCreateDto replenishment,
                                    @PathVariable("id") Long cardId, Model model) {
        model.addAttribute("id", cardId);
        model.addAttribute("replenishment", replenishment);
        return "replenishment/card-replenishment";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("replenishment") ReplenishmentCreateDto replenishment,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpSession session,
                         Model model) {
        session.getAttribute("clientId");
        model.addAttribute("replenishment", replenishment);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("replenishment", replenishment);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/replenishments";
        }
        return (replenishmentService.checkAndCreateReplenishment(replenishment)) ? "replenishment/success" : "replenishment/fail";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!replenishmentService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/replenishments";
    }

    @GetMapping("/replenishments")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAllByFilter(Model model, ReplenishmentFilterDto filter, Pageable pageable) {
        Page<ReplenishmentReadDto> page = replenishmentService.findAllByFilter(filter, pageable);
        model.addAttribute("replenishments", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "replenishment/replenishments";
    }

    @GetMapping("/clientReplenishments")
    public String findAllByClientByFilter(Model model, ReplenishmentFilterDto filter, Pageable pageable, HttpSession session) {
        Page<ReplenishmentReadDto> page = replenishmentService.findByClientByFilter(filter, pageable, (Long) session.getAttribute("clientId"));
        model.addAttribute("replenishments", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "replenishment/clientReplenishments";
    }
}
