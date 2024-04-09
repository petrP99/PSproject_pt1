package com.pers.repository;

import com.pers.entity.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long>,
        FilterClientRepository,
        QuerydslPredicateExecutor<Client> {

    Optional<Client> findByUserId(Long id);

    Optional<Client> findById(Long id);

    Optional<Client> findByUserLogin(String login);




}
