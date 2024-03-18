package com.pers.repository;

import com.pers.entity.Role;
import com.pers.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pers.entity.QUser.user;

@Repository
public class UserRepository extends BaseRepository<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
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

