package com.pers.http.controller;

import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PageResponse;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.service.CardService;
import com.pers.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.io.File;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CardService cardService;
    private final ResourceLoader resourceLoader;

    @GetMapping("/services")
    public String services(@Validated Model model, @ModelAttribute("payment") PaymentCreateDto payment, HttpSession session) {
        var cards = cardService.findActiveCardsAndPositiveBalanceByClientId((Long) session.getAttribute("clientId"));
        model.addAttribute("payment", payment);
        model.addAttribute("cards", cards);
        return "payment/services";
    }

    @GetMapping("/mobile")
    public String payMobile(@Validated Model model, @ModelAttribute("payment") PaymentCreateDto payment, HttpSession session) {
        var cards = cardService.findActiveCardsAndPositiveBalanceByClientId((Long) session.getAttribute("clientId"));
        model.addAttribute("payment", payment);
        model.addAttribute("cards", cards);
        return "payment/mobile";
    }

    @GetMapping("/courses")
    public String payCourses(@Validated Model model, @ModelAttribute("payment") PaymentCreateDto payment, HttpSession session) {
        var cards = cardService.findActiveCardsAndPositiveBalanceByClientId((Long) session.getAttribute("clientId"));
        model.addAttribute("payment", payment);
        model.addAttribute("cards", cards);
        return "payment/courses";
    }

    @GetMapping("/ozon")
    public String payCommunal(@Validated Model model, @ModelAttribute("payment") PaymentCreateDto payment, HttpSession session) {
        var cards = cardService.findActiveCardsAndPositiveBalanceByClientId((Long) session.getAttribute("clientId"));
        model.addAttribute("payment", payment);
        model.addAttribute("cards", cards);
        return "payment/ozon";
    }

    @PostMapping("/create/mobile")
    public String createPayMobile(@Validated @ModelAttribute("payment") PaymentCreateDto payment,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session,
                                  Model model) {
        session.getAttribute("clientId");
        model.addAttribute("payment", payment);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("payment", payment);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/payments/mobile";
        }
        return paymentService.checkAndCreatePayment(payment) ? "payment/success" : "payment/fail";
    }

    @PostMapping("/create/courses")
    public String createPayCourses(@Validated @ModelAttribute("payment") PaymentCreateDto payment,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session,
                                   Model model) {
        session.getAttribute("clientId");
        model.addAttribute("payment", payment);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("payment", payment);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/payments/courses";
        }
        return paymentService.checkAndCreatePayment(payment) ? "payment/success" : "payment/fail";
    }

    @PostMapping("/create/ozon")
    public String createPayCommunal(@Validated @ModelAttribute("payment") PaymentCreateDto payment,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session,
                                    Model model) {
        session.getAttribute("clientId");
        model.addAttribute("payment", payment);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("payment", payment);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/payments/ozon";
        }
        return paymentService.checkAndCreatePayment(payment) ? "payment/success" : "payment/fail";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!paymentService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/payments";
    }

    @GetMapping("/clientPayments")
    public String findAllByClientByFilter(Model model, PaymentFilterDto filter, Pageable pageable, HttpSession session) {
        Page<PaymentReadDto> page = paymentService.findAllByClientByFilter(filter, pageable, (Long) session.getAttribute("clientId"));
        model.addAttribute("payments", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "payment/clientPayments";
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAll(Model model, PaymentFilterDto filter, Pageable pageable) {
        Page<PaymentReadDto> page = paymentService.findAllByFilter(filter, pageable);
        model.addAttribute("payments", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "payment/payments";
    }

//    @GetMapping("/clientPayments/history")
//    public String findAllByClientId(HttpSession session) {
//        List<PaymentReadDto> list = paymentService.findAllByClientId((Long) session.getAttribute("clientId"));
//        return "payment/clientPayments";
//    }

    @SneakyThrows
    @GetMapping("/clientPayments/history")
    public ResponseEntity<byte[]> downloadFile(HttpSession session) {
        File file = paymentService.downloadHistory((Long) session.getAttribute("clientId"));
        FileSystemResource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments history.txt");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .headers(header)
                .body(resource.getContentAsByteArray());
    }


}
