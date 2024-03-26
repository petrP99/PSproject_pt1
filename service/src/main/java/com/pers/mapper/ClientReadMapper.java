package com.pers.mapper;

import com.pers.dto.ClientReadDto;
import com.pers.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientReadMapper implements Mapper<Client, ClientReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public ClientReadDto mapFrom(Client object) {
        return new ClientReadDto(
                object.getId(),
                userReadMapper.mapFrom(object.getUser()),
                object.getBalance(),
                object.getFirstName(),
                object.getLastName(),
                object.getPhone(),
                object.getStatus(),
                object.getCreatedTime()
        );
    }
}
