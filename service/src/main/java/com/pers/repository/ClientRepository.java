package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface ClientRepository extends Repository<Client, Long> {

    Optional<Client> findById(Long id);

}
