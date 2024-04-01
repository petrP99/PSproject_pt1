package com.pers.service;

import com.pers.dto.UserCreateDto;
import com.pers.dto.UserFilterDto;
import com.pers.dto.UserReadDto;
import com.pers.mapper.UserCreateMapper;
import com.pers.mapper.UserReadMapper;
import com.pers.repository.FilterUserRepository;
import com.pers.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapFrom)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::mapFrom);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateMapper::mapFrom)
                .map(userRepository::save)
                .map(userReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public List<UserReadDto> findAll(UserFilterDto filter) {
        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::mapFrom)
                .toList();
    }
    }









