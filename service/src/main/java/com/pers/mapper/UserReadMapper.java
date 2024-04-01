package com.pers.mapper;

import com.pers.dto.UserReadDto;
import com.pers.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getLogin(),
                object.getPassword(),
                object.getRole());
    }

//    @Override
//    public UserReadDto mapFrom(User object) {
//        return new UserReadDto(
//                object.getId(),
//                object.getLogin(),
//                object.getPassword(),
//                object.getRole());
//    }
}
