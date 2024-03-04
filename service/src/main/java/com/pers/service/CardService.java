package com.pers.service;

import com.pers.dao.CardRepository;
import com.pers.dto.CardCreateDto;
import com.pers.dto.CardReadDto;
import com.pers.mapper.CardCreateMapper;
import com.pers.mapper.CardReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateMapper cardCreateMapper;

    public Long create(CardCreateDto cardDto) {
        var cardEntity = cardCreateMapper.mapFrom(cardDto);
        return cardRepository.save(cardEntity).getId();
    }

    public Optional<CardReadDto> findById(Long id) {
        return cardRepository.findById(id).map(cardReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBeCard = cardRepository.findById(id);
        mayBeCard.ifPresent(card -> cardRepository.delete(card.getId()));
        return mayBeCard.isPresent();
    }
}
