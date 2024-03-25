package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Replenishment;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface ReplenishmentRepository extends Repository<Replenishment, Long> {

    Optional<Replenishment> findById(Long id);

}
