package com.pers.repository.filter;

import com.pers.dto.filter.CardFilterDto;
import com.pers.entity.Card;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilterCardRepository {


    @Query(value = "select c from Card c",
            countQuery = "select count(distinct c.id) from Card c")
    List<Card> findAllByFilter(CardFilterDto filter);

}
