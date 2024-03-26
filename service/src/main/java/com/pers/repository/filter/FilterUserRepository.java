package com.pers.repository.filter;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;
import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilterDto filter);

}