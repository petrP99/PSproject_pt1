package com.pers.repository;

import com.pers.dto.filter.ClientFilterDto;
import com.pers.entity.Client;

import java.util.List;

import com.pers.repository.filter.FilterClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long>,
        FilterClientRepository,
        QuerydslPredicateExecutor<Client> {

    Optional<Client> findByUserId(Long id);

    Optional<Client> findById(Long id);


}
