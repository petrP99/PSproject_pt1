package com.pers.repository;

import com.pers.entity.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface CardRepository extends Repository<Card, Long> {

    Optional<Card> findById(Long id);

    @Query("select c from Card c where c.cardNo = :cardNo")
    Optional<Card> findByCardNoFrom(Integer cardNo);

    @Query("select c from Card c where c.cardNo = :cardNo")
    Optional<Card> findByCardNoTo(Integer cardNo);

}
