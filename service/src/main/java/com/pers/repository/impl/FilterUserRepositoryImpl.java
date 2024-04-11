package com.pers.repository.impl;

import com.pers.dto.filter.UserFilterDto;
import com.pers.entity.*;
import com.pers.repository.filter.QPredicate;
import com.pers.repository.FilterUserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.RequiredArgsConstructor;

import static com.pers.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilterDto userFilterDto) {
        var predicate = QPredicate.builder()
                .add(userFilterDto.login(), user.login::containsIgnoreCase)
                .buildOr();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}

