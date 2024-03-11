package com.pers.service;

import com.pers.repository.UserRepository;
import com.pers.dto.UserCreateDto;
import com.pers.dto.UserReadDto;
import com.pers.mapper.UserCreateMapper;
import com.pers.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public Long create(UserCreateDto userDto) {
        var userEntity = userCreateMapper.mapFrom(userDto);
        return userRepository.save(userEntity).getId();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBeUser = userRepository.findById(id);
        mayBeUser.ifPresent(userRepository::delete);
        return mayBeUser.isPresent();
    }
}
