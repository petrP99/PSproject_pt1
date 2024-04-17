package com.pers.repository;

import com.pers.dto.filter.ReplenishmentFilterDto;
import com.pers.entity.Replenishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterReplenishmentRepository {

    Page<Replenishment> findAllByFilter(ReplenishmentFilterDto filterDto, Pageable pageable);

}
