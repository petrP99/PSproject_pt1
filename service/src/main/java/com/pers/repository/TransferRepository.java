package com.pers.repository;

import com.pers.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface TransferRepository extends JpaRepository<Transfer, Long>,
        FilterTransferRepository,
        QuerydslPredicateExecutor<Transfer> {

    @Query("select t from Transfer t where t.cardNoFrom.client.id = :id")
    List<Transfer> findAllByCardNoFromClientId(Long id);

}
