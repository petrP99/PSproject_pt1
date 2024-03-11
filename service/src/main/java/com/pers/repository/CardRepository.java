package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Status;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pers.entity.QCard.card;

@Repository
public class CardRepository extends BaseRepository<Long, Card> {

    public CardRepository(EntityManager entityManager) {
        super(Card.class, entityManager);
    }

    public List<Card> findByCardId(Long id) {
        return new JPAQuery<Card>(getEntityManager())
                .select(card)
                .from(card)
                .where(card.id.eq(id))
                .fetch();
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
