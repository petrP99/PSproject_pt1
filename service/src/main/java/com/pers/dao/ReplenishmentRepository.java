package com.pers.dao;

import com.pers.entity.QReplenishment;
import com.pers.entity.Replenishment;
import com.pers.entity.Transfer;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pers.entity.QReplenishment.replenishment;
import static com.pers.entity.QTransfer.transfer;

public class ReplenishmentRepository extends BaseRepository<Long, Replenishment> {

    public ReplenishmentRepository(EntityManager entityManager) {
        super(Replenishment.class, entityManager);
    }

    public Optional<Replenishment> findById(Long id) {
        return Optional.empty();
    }

    public List<Replenishment> findByClientIdAndCardNo(Long clientId, Integer cardNo) {
        return new JPAQuery<Replenishment>(getEntityManager())
                .select(replenishment)
                .from(replenishment)
                .where(replenishment.clientTo.id.eq(clientId).and(replenishment.cardNoTo.cardNo.eq(cardNo)))
                .fetch();
    }
}
