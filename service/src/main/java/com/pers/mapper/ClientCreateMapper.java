package com.pers.mapper;

import com.pers.dao.UserRepository;
import com.pers.dto.ClientCreateDto;
import com.pers.entity.Client;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientCreateMapper implements Mapper<ClientCreateDto, Client> {

    private UserRepository userRepository;

    @Override
    public Client mapFrom(ClientCreateDto object) {
        return Client.builder()
                .user(userRepository.findById(object.userId()).orElseThrow(IllegalArgumentException::new))
                .balance(object.balance())
                .firstName(object.firstName())
                .lastName(object.lastName())
                .status(object.status())
                .createdTime(object.createdTime())
                .build();
    }
}
