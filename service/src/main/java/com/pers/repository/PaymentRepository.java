package com.pers.repository;

import com.pers.entity.Payment;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pers.entity.QPayment.payment;

@Repository
public class PaymentRepository extends BaseRepository<Long, Payment> {
    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }

    public List<Payment> findByClientIdAndCardNo(Long clientId, Integer cardNo) {
        return new JPAQuery<Payment>(getEntityManager())
                .select(payment)
                .from(payment)
                .where(payment.client.id.eq(clientId).and(payment.card.cardNo.eq(cardNo)))
                .fetch();
    }
}
