package com.pers.dao;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.QCard;
import com.pers.entity.Status;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pers.entity.QCard.*;
import static com.pers.entity.QClient.client;

public class CardRepository extends BaseRepository<Long, Card> {
    public CardRepository(EntityManager entityManager) {
        super(Card.class, entityManager);
    }

    public Optional<Card> findById(Long id) {
        return Optional.empty();
    }

    public List<Card> findByClientId(Long clientId) {
        return new JPAQuery<Card>(getEntityManager())
                .select(card)
                .from(card)
                .where(card.client.id.eq(clientId))
                .fetch();
    }

    public List<Card> findByStatus(Status status) {
        return new JPAQuery<Card>(getEntityManager())
                .select(card)
                .from(card)
                .where(card.status.eq(status))
                .fetch();
    }
}
