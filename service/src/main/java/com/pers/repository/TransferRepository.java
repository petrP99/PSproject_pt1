package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Transfer;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface TransferRepository extends Repository<Transfer, Long> {

    Optional<Transfer> findById(Long id);

}
