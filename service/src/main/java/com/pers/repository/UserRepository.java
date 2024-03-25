package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface UserRepository extends Repository<User, Long> {

    Optional<User> findById(Long id);

}

