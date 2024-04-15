package com.pers.repository;

import com.pers.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>,
        FilterPaymentRepository,
        QuerydslPredicateExecutor<Payment> {

    List<Payment> findByClientId(Long clientId);

}
