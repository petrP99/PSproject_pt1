package com.pers.repository.impl;

import com.pers.dto.UserFilterDto;
import com.pers.entity.User;
import com.pers.repository.QPredicate;
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
    public List<User> findAllByFilter(UserFilterDto filter) {
        var predicate = QPredicate.builder()
                .add(filter.login(), user.login::eq)
                .add(filter.login(), user.login::containsIgnoreCase)
                .add(filter.role(), user.role::eq)
                .buildAnd();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
