package com.pers.repository;

import com.pers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


public interface CardRepository extends JpaRepository<Card, Long>,
        FilterCardRepository,
        QuerydslPredicateExecutor<Card> {

    Optional<Card> findByClientId(Long clientId);
}
