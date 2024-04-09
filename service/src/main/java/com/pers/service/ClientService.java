package com.pers.service;

import com.pers.dto.ClientReadDto;
import com.pers.dto.ClientCreateDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.mapper.ClientCreateMapper;
import com.pers.mapper.ClientReadMapper;
import com.pers.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;


@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;


    public Page<ClientReadDto> findAll(ClientFilterDto filter, Pageable pageable) {
        return clientRepository.findAllByFilter(filter, pageable)
                .map(clientReadMapper::mapFrom);
    }

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::mapFrom)
                .toList();
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

    public Optional<ClientReadDto> findByUserName(String username) {
        return clientRepository.findByUserLogin(username)
                .map(clientReadMapper::mapFrom);
    }


}