package com.pers.repository.impl;

import com.pers.dto.filter.CardFilterDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import static com.pers.entity.QCard.card;
import com.pers.entity.QPayment;
import static com.pers.entity.QPayment.payment;
import com.pers.repository.filter.FilterPaymentRepository;
import com.pers.repository.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilterPaymentRepositoryImpl implements FilterPaymentRepository {

    private final EntityManager entityManager;

    @Override
    public List<Payment> findAllByFilter(PaymentFilterDto filter) {
        var predicate = QPredicate.builder()
                .add(filter.clientId(), payment.client.id::eq)
                .add(filter.shopName(), payment.shopName::containsIgnoreCase)
                .add(filter.timeOfPay(), payment.timeOfPay::after)
                .add(filter.timeOfPay(), payment.timeOfPay::before)
                .add(filter.timeOfPay(), payment.timeOfPay::eq)
                .add(filter.amount(), payment.amount::eq)
                .add(filter.status(), payment.status::eq)
                .buildAnd();

        return new JPAQuery<Payment>(entityManager)
                .select(payment)
                .from(payment)
                .where(predicate)
                .fetch();
    }
}