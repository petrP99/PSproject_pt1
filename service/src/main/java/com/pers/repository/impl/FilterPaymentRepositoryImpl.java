package com.pers.repository.impl;

import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Payment;
import static com.pers.entity.QPayment.payment;
import com.pers.repository.FilterPaymentRepository;
import com.pers.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FilterPaymentRepositoryImpl implements FilterPaymentRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Payment> findAllByFilter(PaymentFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.id(), payment.id::eq)
                .add(filter.clientId(), payment.client.id::eq)
                .add(filter.shopName(), payment.shopName::containsIgnoreCase)
                .add(filter.amount(), payment.amount::eq)
                .add(filter.cardId(), payment.card.id::eq)
                .add(filter.status(), payment.status::eq)
                .buildAnd();

        var query = new JPAQuery<Payment>(entityManager)
                .select(payment)
                .from(payment)
                .where(predicate);

        List<Payment> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }

    @Override
    public Page<Payment> findAllByClientByFilter(PaymentFilterDto filter, Pageable pageable, Long clientId) {
        var predicate = QPredicate.builder()
                .add(filter.id(), payment.id::eq)
                .add(clientId, payment.client.id::eq)
                .add(filter.shopName(), payment.shopName::containsIgnoreCase)
                .add(filter.amount(), payment.amount::eq)
                .add(filter.cardId(), payment.card.id::eq)
                .add(filter.status(), payment.status::eq)
                .buildAnd();

        var query = new JPAQuery<Payment>(entityManager)
                .select(payment)
                .from(payment)
                .where(predicate);

        List<Payment> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }
}