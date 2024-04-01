package com.pers.repository;

import com.pers.dto.UserFilterDto;
import com.pers.entity.Card;
import com.pers.entity.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, FilterUserRepository {

    Optional<User> findById(Long id);
    List<User> findAll();
    List<User> findAllByFilter(UserFilterDto filter);



}

