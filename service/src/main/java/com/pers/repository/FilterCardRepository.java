package com.pers.repository;

import com.pers.dto.filter.CardFilterDto;
import com.pers.dto.filter.ClientFilterDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterCardRepository {

    Page<Card> findAllByFilter(CardFilterDto filterDto, Pageable pageable);

}
