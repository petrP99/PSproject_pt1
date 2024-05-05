package com.pers.mapper;

import com.pers.dto.ClientCreateDto;
import com.pers.entity.Client;
import static com.pers.entity.Status.ACTIVE;
import static com.pers.entity.Status.BLOCKED;
import com.pers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClientCreateMapper implements Mapper<ClientCreateDto, Client> {

    private final UserRepository userRepository;

    @Override
    public Client mapFrom(ClientCreateDto object) {
        return Client.builder()
                .user(userRepository.findById(object.userId()).orElseThrow(IllegalArgumentException::new))
                .balance(object.balance())
                .firstName(object.firstName())
                .lastName(object.lastName())
                .phone(object.phone())
                .status(ACTIVE)
                .createdTime(Instant.now())
                .build();
    }

    @Override
    public Client map(ClientCreateDto fromObject, Client toObject) {
        toObject.setUser(userRepository.findById(fromObject.userId()).orElseThrow(IllegalArgumentException::new));
        toObject.setBalance(fromObject.balance());
        toObject.setFirstName(fromObject.firstName());
        toObject.setLastName(fromObject.lastName());
        toObject.setPhone(fromObject.phone());
        toObject.setStatus(fromObject.status() == ACTIVE ? BLOCKED : ACTIVE);
        toObject.setCreatedTime(Instant.now());
        return toObject;
    }
}
