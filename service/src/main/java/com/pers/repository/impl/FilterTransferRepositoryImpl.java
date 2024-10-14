package com.pers.repository.impl;

import com.pers.dto.filter.TransferFilterDto;
import com.pers.entity.Transfer;
import com.pers.repository.FilterTransferRepository;
import com.pers.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.pers.entity.QTransfer.transfer;

@RequiredArgsConstructor
public class FilterTransferRepositoryImpl implements FilterTransferRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Transfer> findAllByFilter(TransferFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.id(), transfer.id::eq)
                .add(filter.cardNoFrom(), transfer.cardNoFrom.id::eq)
                .add(filter.cardNoTo(), transfer.cardNoTo.id::eq)
                .add(filter.amount(), transfer.amount::eq)
                .add(filter.message(), transfer.message::containsIgnoreCase)
                .add(filter.recipient(), transfer.recipient::containsIgnoreCase)
                .add(filter.status(), transfer.status::eq)
                .buildAnd();

        var query = new JPAQuery<Transfer>(entityManager)
                .select(transfer)
                .from(transfer)
                .where(predicate);

        List<Transfer> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }

    @Override
    public Page<Transfer> findAllByClientByFilter(TransferFilterDto filter, Pageable pageable, Long clientId) {
        var predicate = QPredicate.builder()
                .add(filter.id(), transfer.id::eq)
                .add(clientId, transfer.clientId.id::eq)
                .add(filter.cardNoFrom(), transfer.cardNoFrom.id::eq)
                .add(filter.cardNoTo(), transfer.cardNoTo.id::eq)
                .add(filter.amount(), transfer.amount::eq)
                .add(filter.message(), transfer.message::containsIgnoreCase)
                .add(filter.recipient(), transfer.recipient::containsIgnoreCase)
                .add(filter.status(), transfer.status::eq)
                .buildAnd();

        var query = new JPAQuery<Transfer>(entityManager)
                .select(transfer)
                .from(transfer)
                .where(predicate);

        List<Transfer> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }
}