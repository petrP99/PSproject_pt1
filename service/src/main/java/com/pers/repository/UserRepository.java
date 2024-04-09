package com.pers.repository;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.User;

import java.util.List;

import com.pers.repository.filter.FilterUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>,
        FilterUserRepository,
        QuerydslPredicateExecutor<User> {

    Optional<User> findById(Long id);

    List<User> findAll();
    
}

