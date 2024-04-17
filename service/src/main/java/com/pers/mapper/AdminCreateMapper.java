package com.pers.mapper;

import com.pers.dto.UserCreateDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminCreateMapper implements Mapper<UserCreateDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapFrom(UserCreateDto object) {
        return copy(object);
    }

    @Override
    public User map(UserCreateDto fromObject, User toObject) {
        var user = copy(fromObject);
        toObject.setLogin(user.getLogin());
        toObject.setPassword(user.getPassword());
        toObject.setRole(user.getRole());
        return toObject;
    }

    public User copy(UserCreateDto object) {
        User user = new User();
        user.setLogin(object.getLogin());
        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
        user.setRole(Role.ADMIN);
        return user;
    }
}
