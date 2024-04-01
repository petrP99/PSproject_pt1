package com.pers.repository;

import com.pers.dto.UserFilterDto;
import com.pers.entity.User;
import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilterDto filter);

}