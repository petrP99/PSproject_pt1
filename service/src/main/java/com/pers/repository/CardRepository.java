package com.pers.repository;

import com.pers.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface CardRepository extends JpaRepository<Card, Long>,
        FilterCardRepository,
        QuerydslPredicateExecutor<Card> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Card> findByClientId(Long clientId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Card> findById(Long id);

    List<Card> findByClientPhone(String phone);

}
