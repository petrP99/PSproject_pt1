package com.pers.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface FilterUserRepository {

    @Query("select u from User u where u.role = 'USER'")
    Page<User> findAllByFilter(UserFilterDto userFilterDto, Pageable pageable);

}