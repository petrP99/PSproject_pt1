package com.pers.service;


import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.mapper.PaymentCreateMapper;
import com.pers.mapper.PaymentReadMapper;
import com.pers.repository.PaymentRepository;
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
        return paymentRepository.findAllByFilter(filter, pageable)
                .map(paymentReadMapper::mapFrom);
    }

}
