package com.pers.dao;

import com.pers.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    @Getter
    private static final UserDao INSTANCE = new UserDao();

    public List<User> findAll(Session session) {
        return Collections.emptyList();
    }

    public List<User> findByLogin(Session session, String login) {
        return Collections.emptyList();
    }

    public List<User> findAllOrderBy(Session session) {
        return Collections.emptyList();
    }

}
