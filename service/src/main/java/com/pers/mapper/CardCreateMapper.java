package com.pers.mapper;

import com.pers.entity.Status;
import com.pers.repository.ClientRepository;
import com.pers.dto.CardCreateDto;
import com.pers.entity.Card;
import com.pers.util.GenerateNumberCardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CardCreateMapper implements Mapper<CardCreateDto, Card> {

    private final ClientRepository clientRepository;

    @Override
    public Card mapFrom(CardCreateDto object) {
        return Card.builder()
                .client(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .balance(new BigDecimal(0))
                .createdDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(5L))
                .status(Status.ACTIVE)
                .build();
    }
}
