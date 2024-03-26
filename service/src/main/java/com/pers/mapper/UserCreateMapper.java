package com.pers.mapper;

import com.pers.dto.UserCreateDto;
import com.pers.entity.Role;
import com.pers.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
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
        user.setPassword(object.getPassword());
        user.setRole(Role.USER);
        return user;
    }
}
