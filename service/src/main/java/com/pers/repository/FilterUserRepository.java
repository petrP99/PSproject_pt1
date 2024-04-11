package com.pers.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FilterUserRepository {

    Page<User> findAllByFilter(UserFilterDto userFilterDto);


}