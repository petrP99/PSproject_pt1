package com.pers.repository;

import com.pers.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>,
        FilterUserRepository,
        QuerydslPredicateExecutor<User> {

    Optional<User> findByLogin(String login);

    @Query("select u.id from User u where u.login = :login")
    Long findIdByLogin(String login);

}

