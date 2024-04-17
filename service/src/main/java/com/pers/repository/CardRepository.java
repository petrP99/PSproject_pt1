package com.pers.repository;

import com.pers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface CardRepository extends JpaRepository<Card, Long>,
        FilterCardRepository,
        QuerydslPredicateExecutor<Card> {

    List<Card> findByClientId(Long clientId);
}
