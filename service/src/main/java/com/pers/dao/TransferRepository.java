package com.pers.dao;

import com.pers.entity.Card;
import com.pers.entity.QTransfer;
import com.pers.entity.Status;
import com.pers.entity.Transfer;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pers.entity.QCard.card;
import static com.pers.entity.QTransfer.transfer;

public class TransferRepository extends BaseRepository<Long, Transfer> {
    public TransferRepository(EntityManager entityManager) {
        super(Transfer.class, entityManager);
    }

    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }

    public List<Transfer> findByCardNoTo(Integer cardId) {
        return new JPAQuery<Transfer>(getEntityManager())
                .select(transfer)
                .from(transfer)
                .where(transfer.cardNoTo.cardNo.eq(cardId))
                .fetch();
    }
}
