package com.pers.repository;

import com.pers.entity.Client;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pers.entity.QClient.client;

@Repository
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
