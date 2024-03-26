package com.pers.service;


import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PaymentFilterDto;
import static com.pers.entity.QPayment.payment;
import com.pers.mapper.PaymentCreateMapper;
import com.pers.mapper.PaymentReadMapper;
import com.pers.repository.PaymentRepository;
import com.pers.repository.filter.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentReadMapper paymentReadMapper;
    private final PaymentCreateMapper paymentCreateMapper;

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

    public List<PaymentReadDto> findByClientId(Long clientId) {
        return paymentRepository.findByClientId(clientId).stream()
                .map(paymentReadMapper::mapFrom)
                .toList();
    }

    public Page<PaymentReadDto> findAllByFilter(PaymentFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.clientId(), payment.client.id::eq)
                .add(filter.shopName(), payment.shopName::containsIgnoreCase)
                .add(filter.timeOfPay(), payment.timeOfPay::after)
                .add(filter.timeOfPay(), payment.timeOfPay::before)
                .add(filter.timeOfPay(), payment.timeOfPay::eq)
                .add(filter.amount(), payment.amount::eq)
                .add(filter.status(), payment.status::eq)
                .buildAnd();

        return paymentRepository.findAll(predicate, pageable)
                .map(paymentReadMapper::mapFrom);
    }

}
