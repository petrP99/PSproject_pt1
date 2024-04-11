package com.pers.service;

import com.pers.dto.ClientReadDto;
import com.pers.dto.ClientCreateDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.entity.QClient;
import static com.pers.entity.QClient.*;
import com.pers.mapper.ClientCreateMapper;
import com.pers.mapper.ClientReadMapper;
import com.pers.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import com.pers.repository.filter.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;


    public Page<ClientReadDto> findAll(ClientFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.firstName(), client.firstName::containsIgnoreCase)
                .add(filter.lastName(), client.lastName::containsIgnoreCase)
                .add(filter.phone(), client.phone::containsIgnoreCase)
                .add(filter.status(), client.status::eq)
                .add(filter.balance(), client.balance::eq)
                .buildAnd();


        return clientRepository.findAll(predicate, pageable)
                .map(clientReadMapper::mapFrom);
    }

    public Optional<ClientReadDto> findById(Long id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::mapFrom);
    }

    @Transactional
    public ClientReadDto create(ClientCreateDto clientDto) {
        return Optional.of(clientDto)
                .map(clientCreateMapper::mapFrom)
                .map(clientRepository::save)
                .map(clientReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public Optional<ClientReadDto> update(Long id, ClientCreateDto clientDto) {
        return clientRepository.findById(id)
                .map(entity -> clientCreateMapper.map(clientDto, entity))
                .map(clientRepository::saveAndFlush)
                .map(clientReadMapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        return clientRepository.findById(id)
                .map(entity -> {
                    clientRepository.delete(entity);
                    clientRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<ClientReadDto> findByUserId(Long userId) {
        return clientRepository.findByUserId(userId)
                .map(clientReadMapper::mapFrom);
    }
}