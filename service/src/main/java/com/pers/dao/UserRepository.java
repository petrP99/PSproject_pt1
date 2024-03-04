package com.pers.dao;

import com.pers.entity.QUser;
import com.pers.entity.Role;
import com.pers.entity.User;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pers.entity.QUser.user;

public class UserRepository extends BaseRepository<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findAll() {
        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .fetch();
    }

    public List<User> findAllById(Long id) {
        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetch();
    }

    public List<User> findAllByRole(Role role) {
        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.role.eq(role))
                .fetch();
    }



}

