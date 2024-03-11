package com.pers.service;

import com.pers.repository.ClientRepository;
import com.pers.dto.ClientCreateDto;
import com.pers.dto.ClientReadDto;
import com.pers.mapper.ClientCreateMapper;
import com.pers.mapper.ClientReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;

    public Long create(ClientCreateDto clientDto) {
        var clientEntity = clientCreateMapper.mapFrom(clientDto);
        return clientRepository.save(clientEntity).getId();
    }

    public Optional<ClientReadDto> findById(Long id) {
        return clientRepository.findById(id).map(clientReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBeClient = clientRepository.findById(id);
        mayBeClient.ifPresent(clientRepository::delete);
        return mayBeClient.isPresent();
    }
}
