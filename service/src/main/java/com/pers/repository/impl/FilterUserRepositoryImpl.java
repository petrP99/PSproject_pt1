package com.pers.repository.impl;


import com.pers.dto.filter.UserFilterDto;
import static com.pers.entity.QUser.user;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.pers.repository.FilterUserRepository;
import com.pers.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    public Page<User> findAllByFilter(UserFilterDto userFilterDto, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(userFilterDto.login(), user.login::containsIgnoreCase)
                .buildOr();

        JPAQuery<User> query = new JPAQuery<>(entityManager)
                .select(user)
                .from(user)
                .where(predicate, user.role.eq(Role.USER));

        List<User> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }

    public Page<User> findAdminByFilter(UserFilterDto userFilterDto, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(userFilterDto.login(), user.login::containsIgnoreCase)
                .buildOr();

        JPAQuery<User> query = new JPAQuery<>(entityManager)
                .select(user)
                .from(user)
                .where(predicate, user.role.eq(Role.ADMIN));

        List<User> content = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = query.fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }
}