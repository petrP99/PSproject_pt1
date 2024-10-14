package com.pers.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pers.dto.CardUpdateBalanceDto;
import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Status;
import com.pers.mapper.PaymentCreateMapper;
import com.pers.mapper.PaymentReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.PaymentRepository;
import com.pers.util.CheckOfOperationUtil;
import com.pers.util.DateTimeParserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pers.entity.Status.FAILED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentReadMapper paymentReadMapper;
    private final PaymentCreateMapper paymentCreateMapper;
    private final ClientService clientService;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final SmsService smsService;

    @Transactional
    public boolean checkAndCreatePayment(PaymentCreateDto payment) {
        var clientReadDto = clientService.findById(payment.clientId()).orElseThrow();
        var cardReadDto = cardService.findById(payment.cardId()).orElseThrow();
        var amount = payment.amount();

        if (clientReadDto.getStatus() == Status.ACTIVE
                && cardReadDto.status() == Status.ACTIVE
                && amount.compareTo(cardReadDto.balance()) <= 0) {

            create(payment);
            var cardCreateDto = new CardUpdateBalanceDto(
                    cardReadDto.id(),
                    cardReadDto.clientId(),
                    cardReadDto.balance().subtract(amount),
                    cardReadDto.createdDate(),
                    cardReadDto.expireDate(),
                    cardReadDto.status());

            cardService.updateCardBalance(cardCreateDto);

            var newBalance = CheckOfOperationUtil.calculateClientBalance(cardRepository.findByClientId(payment.clientId()));
            var clientUpdateBalanceDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientReadDto, newBalance);
//            smsService.sendInfoPayment(payment, clientReadDto, newBalance);
            clientService.updateBalance(clientUpdateBalanceDto);
        } else {
            var paymentFail = new PaymentCreateDto(
                    payment.shopName(),
                    payment.amount(),
                    payment.clientId(),
                    payment.cardId(),
                    payment.timeOfPay(),
                    FAILED);
            create(paymentFail);
            return false;
        }
        return true;
    }

    @Transactional
    public PaymentReadDto create(PaymentCreateDto paymentDto) {
        return Optional.of(paymentDto)
                .map(paymentCreateMapper::mapFrom)
                .map(paymentRepository::save)
                .map(paymentReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return paymentRepository.findById(id)
                .map(entity -> {
                    paymentRepository.delete(entity);
                    paymentRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Page<PaymentReadDto> findAllByFilter(PaymentFilterDto filter, Pageable pageable) {
        return paymentRepository.findAllByFilter(filter, pageable)
                .map(paymentReadMapper::mapFrom);
    }

    public Page<PaymentReadDto> findAllByClientByFilter(PaymentFilterDto filter, Pageable pageable, Long clientId) {
        return paymentRepository.findAllByClientByFilter(filter, pageable, clientId)
                .map(paymentReadMapper::mapFrom);
    }

    public Optional<PaymentReadDto> findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentReadMapper::mapFrom);
    }

    public List<PaymentReadDto> findAllByClientId(Long id) {
        return paymentRepository.findByClientId(id).stream()
                .map(paymentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public byte[] downloadHistory(Long id) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        List<PaymentReadDto> allByClientId = findAllByClientId(id);
        document.open();
        for (PaymentReadDto paymentReadDto : allByClientId) {
            document.add(new Paragraph(String.format("%s: %s, amount %s rub, by card *%s, in %s",
                    paymentReadDto.status().toString().toLowerCase(),
                    paymentReadDto.shopName(),
                    paymentReadDto.amount(),
                    paymentReadDto.cardId().toString().substring(2),
                    DateTimeParserUtil.dateTimeParser(paymentReadDto.timeOfPay().toString()))));
        }
        document.close();
        return outputStream.toByteArray();
    }
}