package com.pers.service;

import com.pers.dto.UserReadDtoHttp;
import com.pers.mapper.UserReadMapper;
import com.pers.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public List<UserReadDtoHttp> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapFrom)
                .toList();
    }
}
