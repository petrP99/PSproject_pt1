package com.pers.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FilterUserRepository {

    Page<User> findAllByFilter(UserFilterDto userFilterDto, Pageable pageable);

}