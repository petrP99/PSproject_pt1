package com.pers.service;

import com.pers.dto.CardUpdateBalanceDto;
import com.pers.dto.CardCreateDto;
import com.pers.dto.CardReadDto;
import com.pers.dto.filter.CardFilterDto;

import com.pers.dto.filter.CardStatusDto;
import com.pers.mapper.CardCreateMapper;
import com.pers.mapper.CardReadMapper;
import com.pers.mapper.CardUpdateBalanceMapper;
import com.pers.repository.CardRepository;
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
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateMapper cardCreateMapper;
    private final CardUpdateBalanceMapper cardUpdateBalanceMapper;

    public Optional<CardReadDto> findById(Long id) {
        return cardRepository.findById(id)
                .map(cardReadMapper::mapFrom);
    }

    @Transactional
    public CardReadDto create(CardCreateDto cardDto) {
        return Optional.of(cardDto)
                .map(cardCreateMapper::mapFrom)
                .map(cardRepository::save)
                .map(cardReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<CardReadDto> updateStatus(CardReadDto cardDto) {
        return Optional.of(cardDto)
                .map(cardCreateMapper::mapStatus)
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::mapFrom);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CardReadDto updateBalance(CardUpdateBalanceDto cardDto) {
        return Optional.of(cardDto)
                .map(cardUpdateBalanceMapper::mapFrom)
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return cardRepository.findById(id)
                .map(entity -> {
                    cardRepository.delete(entity);
                    cardRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public List<CardReadDto> findByClientId(Long clientId) {
        return cardRepository.findByClientId(clientId).stream()
                .map(cardReadMapper::mapFrom)
                .toList();
    }

    public Page<CardReadDto> findAllByFilter(CardFilterDto filter, Pageable pageable) {
        return cardRepository.findAllByFilter(filter, pageable)
                .map(cardReadMapper::mapFrom);
    }
}
