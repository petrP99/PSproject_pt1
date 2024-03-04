package com.pers.mapper;

import com.pers.dto.CardReadDto;
import com.pers.entity.Card;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardReadMapper implements Mapper<Card, CardReadDto> {

    CardReadMapper cardReadMapper;

    @Override
    public CardReadDto mapFrom(Card object) {
        return new CardReadDto(
                object.getId(),
                object.getClient().getId(),
                object.getCardNo(),
                object.getBalance(),
                object.getCreatedDate(),
                object.getExpireDate(),
                object.getStatus()
        );
    }
}
