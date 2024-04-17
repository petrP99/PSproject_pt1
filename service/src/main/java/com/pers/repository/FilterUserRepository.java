package com.pers.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterUserRepository {

    Page<User> findAllByFilter(UserFilterDto userFilterDto, Pageable pageable);

}