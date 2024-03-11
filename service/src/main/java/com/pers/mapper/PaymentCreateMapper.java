package com.pers.mapper;

import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import com.pers.dto.PaymentCreateDto;
import com.pers.entity.Payment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentCreateMapper implements Mapper<PaymentCreateDto, Payment> {

    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;

    @Override
    public Payment mapFrom(PaymentCreateDto object) {
        return Payment.builder()
                .shopName(object.shopName())
                .amount(object.amount())
                .client(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .card(cardRepository.findById(object.cardId()).orElseThrow(IllegalArgumentException::new))
                .timeOfPay(object.timeOfPay())
                .status(object.status())
                .build();
    }
}
