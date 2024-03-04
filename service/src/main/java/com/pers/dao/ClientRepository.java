package com.pers.dao;

import com.pers.entity.Client;
import com.pers.entity.QClient;
import com.pers.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.pers.entity.QClient.client;
import static com.pers.entity.QUser.user;

public class ClientRepository extends BaseRepository<Long, Client> {
    public ClientRepository(EntityManager entityManager) {
        super(Client.class, entityManager);
    }

    public List<Client> findByUserId(Long userId) {
        return new JPAQuery<Client>(getEntityManager())
                .select(client)
                .from(client)
                .where(client.user.id.eq(userId))
                .fetch();
    }

}
