package com.pers.mapper;

import com.pers.dao.ClientRepository;
import com.pers.dto.CardCreateDto;
import com.pers.entity.Card;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardCreateMapper implements Mapper<CardCreateDto, Card> {

    private final ClientRepository clientRepository;

    @Override
    public Card mapFrom(CardCreateDto object) {
        return Card.builder()
                .client(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .cardNo(object.cardNo())
                .balance(object.balance())
                .createdDate(object.createdDate())
                .expireDate(object.expireDate())
                .status(object.status())
                .build();
    }
}
