package com.pers.http.controller;

import com.pers.dto.TransferCreateDto;
import com.pers.dto.TransferReadDto;
import com.pers.dto.filter.PageResponse;
import com.pers.dto.filter.TransferFilterDto;
import com.pers.service.CardService;
import com.pers.service.ClientService;
import com.pers.service.TransferService;
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
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;
    private final CardService cardService;
    private final ClientService clientService;

    @GetMapping("/transfer")
    public String transfer(@Validated @ModelAttribute("transfer") TransferCreateDto transfer, Model model, HttpSession session) {
        var clientId = (Long) session.getAttribute("clientId");
        var cards = cardService.findByClientId(clientId).stream().toList();
        model.addAttribute("cards", cards);
        model.addAttribute("transfer", transfer);
        return "transfer/transfer";
    }

    @GetMapping("/check")
    public String checkTransfer(@Validated TransferCreateDto transfer, HttpSession session, Model model) {
        var cardTo = cardService.findById(transfer.cardIdTo()).orElseThrow();
        var recipient = clientService.findFirstAndLastNameByClientId(cardTo.clientId());
        session.setAttribute("transfer", transfer);
        model.addAttribute("recipient", recipient);
        return "transfer/check";
    }

    @PostMapping("/create")
    public String create(@Validated TransferCreateDto transfer, HttpSession session, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        transfer = (TransferCreateDto) session.getAttribute("transfer");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("transfer", transfer);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/transfers";
        }
        return transferService.checkTransfer(transfer);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated TransferCreateDto transfer) {
        return transferService.update(id, transfer)
                .map(it -> "redirect:/transfers/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!transferService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/transfers";
    }

    @GetMapping("/transfers")
    public String findByClientId(@Validated Model model, HttpSession session) {
        var clientId = (Long) session.getAttribute("clientId");
        var myTransfers = transferService.findBySenderClientId(clientId);
        model.addAttribute("transfers", myTransfers);
        return "transfer/transfers";
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public String findAll(Model model, TransferFilterDto filter, Pageable pageable) {
        Page<TransferReadDto> page = transferService.findAllByFilter(filter, pageable);
        model.addAttribute("transfers", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "transfer/transfers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return transferService.findById(id)
                .map(transfer -> {
                    model.addAttribute("transfer", transfer);
                    return "transfer/transfer";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
