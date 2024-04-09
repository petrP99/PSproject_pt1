package com.pers.repository;

import com.pers.dto.filter.CardFilterDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterPaymentRepository {

    Page<Payment> findAllByFilter(PaymentFilterDto filterDto, Pageable pageable);

}
