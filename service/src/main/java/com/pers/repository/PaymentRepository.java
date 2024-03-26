package com.pers.repository;

import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import com.pers.repository.filter.FilterPaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>,
        FilterPaymentRepository,
        QuerydslPredicateExecutor<Payment> {

    List<Payment> findByClientId(Long clientId);

}
