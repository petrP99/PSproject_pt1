package com.pers.unit;

import com.pers.dto.CardReadDto;
import com.pers.dto.ClientReadDto;
import com.pers.dto.UserReadDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.util.CheckOfOperationUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CheckOfOperationUtilTest {

    CardReadDto cardReadDto = new CardReadDto(2324l, Client.builder().id(134343l).build().getId(), BigDecimal.valueOf(500), LocalDate.now(), LocalDate.now().plusYears(1), Status.ACTIVE);


    @Test
    void calculateClientBalance() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.builder()
                .balance(new BigDecimal(100))
                .build());
        cards.add(Card.builder()
                .balance(new BigDecimal(100))
                .build());
        cards.add(Card.builder()
                .balance(new BigDecimal(100))
                .build());

        var result = CheckOfOperationUtil.calculateClientBalance(cards);

        assertEquals(result, BigDecimal.valueOf(300));
    }

    @Test
    void createClientUpdateBalanceDto() {
        var clientReadDto = new ClientReadDto(
                1000l,
                new UserReadDto(1000l, "user@ru", "123", Role.USER),
                BigDecimal.valueOf(100),
                "P",
                "L",
                "89009009009",
                Status.ACTIVE,
                Instant.now());

        var newBalance = new BigDecimal(500);

        var result = CheckOfOperationUtil.createClientUpdateBalanceDto(clientReadDto, newBalance);

        assertEquals(result.balance(), BigDecimal.valueOf(500));
    }

    @Test
    void createDtoCardUpdateBalanceSubtract() {
        var amount = new BigDecimal(250);

        var result = CheckOfOperationUtil.createDtoCardUpdateBalanceSubtract(cardReadDto, amount);

        assertEquals(result.balance(), BigDecimal.valueOf(250));
    }

    @Test
    void createDtoCardUpdateBalanceAdd() {
        var amount = new BigDecimal(250);

        var result = CheckOfOperationUtil.createDtoCardUpdateBalanceAdd(cardReadDto, amount);

        assertEquals(result.balance(), BigDecimal.valueOf(750));
    }
}