package com.pers.http.controller;

import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PageResponse;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.service.CardService;
import com.pers.service.PaymentService;
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
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CardService cardService;

    @GetMapping("/services")
    public String services(@Validated Model model,
                           @ModelAttribute("payment") PaymentCreateDto payment, HttpSession session) {
        var clientId = (Long) session.getAttribute("clientId");
        model.addAttribute("payment", payment);
        var cards = cardService.findByClientId(clientId).stream().toList();
        model.addAttribute("cards", cards);
        return "payment/services";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("payment") PaymentCreateDto payment, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        session.getAttribute("clientId");
        model.addAttribute("payment", payment);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("payment", payment);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/payments";
        }
        return paymentService.checkPayment(payment);
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated PaymentCreateDto payment) {
        return paymentService.update(id, payment)
                .map(it -> "redirect:/payments/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!paymentService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/payments";
    }

    @GetMapping("/mypayments")
    public String findByClientId(@Validated Model model, HttpSession session) {
        session.getAttribute("clientId");
        var clientId = (Long) session.getAttribute("clientId");
        var myPayments = paymentService.findByClientId(clientId);
        model.addAttribute("payments", myPayments);
        return "payment/mypayments";
    }

    @GetMapping("/payments")
//    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAll(Model model, PaymentFilterDto filter, Pageable pageable) {
        Page<PaymentReadDto> page = paymentService.findAllByFilter(filter, pageable);
        model.addAttribute("payments", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "payment/payments";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return paymentService.findById(id)
                .map(payment -> {
                    model.addAttribute("payment", payment);
                    return "payment/payment";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
