package com.pers.repository.impl;

import com.pers.dto.filter.ReplenishmentFilterDto;
import static com.pers.entity.QReplenishment.replenishment;
import com.pers.entity.Replenishment;
import com.pers.repository.FilterReplenishmentRepository;
import com.pers.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RequiredArgsConstructor
public class FilterReplenishmentRepositoryImpl implements FilterReplenishmentRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Replenishment> findAllByFilter(ReplenishmentFilterDto filter, Pageable pageable) {

        var predicate = QPredicate.builder()
                .add(filter.id(), replenishment.id::eq)
                .add(filter.clientId(), replenishment.clientTo.id::eq)
                .add(filter.cardNo(), replenishment.cardNoTo.id::eq)
                .add(filter.amount(), replenishment.amount::eq)
                .add(filter.timeOfReplenishment(), replenishment.timeOfReplenishment::before)
                .add(filter.status(), replenishment.status::eq)
                .buildAnd();

        var query = new JPAQuery<Replenishment>(entityManager)
                .select(replenishment)
                .from(replenishment)
                .where(predicate);

        List<Replenishment> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }
}