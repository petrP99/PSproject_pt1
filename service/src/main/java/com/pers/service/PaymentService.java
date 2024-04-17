package com.pers.service;


import com.pers.dto.CardUpdateBalanceDto;
import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Status;
import static com.pers.entity.Status.FAILED;
import com.pers.mapper.PaymentCreateMapper;
import com.pers.mapper.PaymentReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.PaymentRepository;
import com.pers.util.CheckOfOperationUtil;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public String checkPayment(PaymentCreateDto payment) {

        var clientId = payment.clientId();
        var clientReadDto = clientService.findById(clientId).orElseThrow();
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

            cardService.updateBalance(cardCreateDto);

            var newBalance = CheckOfOperationUtil.updateClientBalance(payment.clientId(), cardRepository);
            var clientUpdateBalanceDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientReadDto, newBalance);

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

            return "payment/fail";
        }
        return "payment/success";
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
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

    public List<PaymentReadDto> findByClientId(Long clientId) {
        return paymentRepository.findByClientId(clientId).stream()
                .map(paymentReadMapper::mapFrom)
                .toList();
    }


    public Page<PaymentReadDto> findAllByFilter(PaymentFilterDto filter, Pageable pageable) {
        return paymentRepository.findAllByFilter(filter, pageable)
                .map(paymentReadMapper::mapFrom);
    }

    @Transactional
    public Optional<PaymentReadDto> update(Long id, PaymentCreateDto paymentDto) {
        return paymentRepository.findById(id)
                .map(entity -> paymentCreateMapper.map(paymentDto, entity))
                .map(paymentRepository::saveAndFlush)
                .map(paymentReadMapper::mapFrom);
    }

    public Optional<PaymentReadDto> findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentReadMapper::mapFrom);
    }

}
