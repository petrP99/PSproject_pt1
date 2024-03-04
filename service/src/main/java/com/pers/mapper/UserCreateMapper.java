package com.pers.mapper;

import com.pers.dto.UserCreateDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .login(object.login())
                .password(object.password())
                .role(Role.USER)
                .build();
    }
}
