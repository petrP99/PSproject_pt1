package com.pers.repository.impl;

import com.pers.dto.filter.ClientFilterDto;
import com.pers.entity.Client;
import static com.pers.entity.QClient.client;
import com.pers.repository.filter.FilterClientRepository;
import com.pers.repository.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class FilterClientRepositoryImpl implements FilterClientRepository {

//    private final EntityManager entityManager;

//    @Override
//    public List<Client> findAllByFilter(ClientFilterDto filter) {
//        var predicate = QPredicate.builder()
//                .add(filter.status(), client.status::eq)
//                .add(filter.firstName(), client.firstName::containsIgnoreCase)
//                .add(filter.lastName(), client.lastName::containsIgnoreCase)
//                .add(filter.phone(), client.phone::containsIgnoreCase)
//                .add(filter.balance(), client.balance::eq)
//                .buildAnd();
//
//        return new JPAQuery<Client>(entityManager)
//                .select(client)
//                .from(client)
//                .where(predicate)
//                .fetch();
//    }
}
