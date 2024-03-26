package com.pers.repository.impl;

import com.pers.dto.filter.CardFilterDto;
import com.pers.entity.Card;
import static com.pers.entity.QCard.card;
import com.pers.repository.filter.FilterCardRepository;
import com.pers.repository.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilterCardRepositoryImpl implements FilterCardRepository {

    private final EntityManager entityManager;

    @Override
    public List<Card> findAllByFilter(CardFilterDto filter) {
        var predicate = QPredicate.builder()
                .add(filter.status(), card.status::eq)
                .add(filter.balance(), card.balance::eq)
                .add(filter.expireDate(), card.expireDate::eq)
                .buildAnd();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }

}