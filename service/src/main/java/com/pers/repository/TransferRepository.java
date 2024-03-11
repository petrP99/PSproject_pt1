package com.pers.repository;

import com.pers.entity.Transfer;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pers.entity.QTransfer.transfer;

@Repository
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
