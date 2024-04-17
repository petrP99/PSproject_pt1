package com.pers.mapper;

import com.pers.dto.CardReadDto;
import com.pers.dto.filter.CardStatusDto;
import com.pers.entity.Status;
import com.pers.repository.ClientRepository;
import com.pers.dto.CardCreateDto;
import com.pers.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CardCreateMapper implements Mapper<CardCreateDto, Card>, MapperStatus<CardReadDto, Card> {

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

    @Override
    public Card mapStatus(CardReadDto object) {
        return Card.builder()
                .id(object.id())
                .client(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .balance(object.balance())
                .createdDate(object.createdDate())
                .expireDate(object.expireDate())
                .status(Status.BLOCKED)
                .build();
    }
}
