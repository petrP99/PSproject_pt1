package com.pers.service;

import com.pers.dto.CardCreateDto;
import com.pers.dto.CardReadDto;
import com.pers.dto.filter.CardFilterDto;
import static com.pers.entity.QCard.card;
import com.pers.mapper.CardCreateMapper;
import com.pers.mapper.CardReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.filter.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Optional<CardReadDto> update(Long id, CardCreateDto cardDto) {
        return cardRepository.findById(id)
                .map(entity -> cardCreateMapper.map(cardDto, entity))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::mapFrom);
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

    public Page<CardReadDto> findAll(CardFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.id(), card.id::eq)
                .add(filter.clientId(), card.client.id::eq)
                .add(filter.balance(), card.balance::eq)
                .add(filter.expireDate(), card.expireDate::before)
                .add(filter.status(), card.status::eq)
                .buildAnd();

        return cardRepository.findAll(predicate, pageable)
                .map(cardReadMapper::mapFrom);
    }
}
