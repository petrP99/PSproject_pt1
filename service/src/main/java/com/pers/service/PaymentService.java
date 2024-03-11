package com.pers.service;


import com.pers.repository.PaymentRepository;
import com.pers.dto.PaymentCreateDto;
import com.pers.dto.PaymentReadDto;
import com.pers.mapper.PaymentCreateMapper;
import com.pers.mapper.PaymentReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PaymentService {


    private final PaymentRepository paymentRepository;
    private final PaymentReadMapper paymentReadMapper;
    private final PaymentCreateMapper paymentCreateMapper;

    public Long create(PaymentCreateDto paymentDto) {
        var paymentEntity = paymentCreateMapper.mapFrom(paymentDto);
        return paymentRepository.save(paymentEntity).getId();
    }

    public Optional<PaymentReadDto> findById(Long id) {
        return paymentRepository.findById(id).map(paymentReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBePayment = paymentRepository.findById(id);
        mayBePayment.ifPresent(paymentRepository::delete);
        return mayBePayment.isPresent();
    }
    
}
