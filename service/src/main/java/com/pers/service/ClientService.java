package com.pers.service;

import com.pers.dto.ClientReadDtoHTTP;
import com.pers.mapper.ClientReadMapper;
import com.pers.repository.ClientRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
//    private final ClientReadMapper clientReadMapper;

//    public List<ClientReadDtoHTTP> findAll() {
//        return clientRepository.findAll().stream()
//                .map(clientReadMapper::mapFrom)
//                .toList();
//    }
}

