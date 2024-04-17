package com.pers.util;

import com.pers.dto.CardReadDto;
import com.pers.dto.CardUpdateBalanceDto;
import com.pers.dto.ClientReadDto;
import com.pers.dto.ClientUpdateBalanceDto;
import com.pers.entity.Card;
import com.pers.repository.CardRepository;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class CheckOfOperationUtil {

    public BigDecimal updateClientBalance(Long clientId, CardRepository cardRepository) {
        return cardRepository.findByClientId(clientId).stream()
                .map(Card::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public ClientUpdateBalanceDto createClientUpdateBalanceDto(ClientReadDto clientReadDto, BigDecimal newBalance) {
        return new ClientUpdateBalanceDto(
                clientReadDto.getId(),
                clientReadDto.getUserId().getId(),
                newBalance,
                clientReadDto.getFirstName(),
                clientReadDto.getLastName(),
                clientReadDto.getPhone(),
                clientReadDto.getStatus(),
                clientReadDto.getCreatedTime());
    }

    public CardUpdateBalanceDto cardUpdateBalanceSubtract(CardReadDto cardReadDto, BigDecimal amount) {
        return new CardUpdateBalanceDto(
                cardReadDto.id(),
                cardReadDto.clientId(),
                cardReadDto.balance().subtract(amount),
                cardReadDto.createdDate(),
                cardReadDto.expireDate(),
                cardReadDto.status());
    }

    public CardUpdateBalanceDto cardUpdateBalanceAdd(CardReadDto cardReadDto, BigDecimal amount) {
        return new CardUpdateBalanceDto(
                cardReadDto.id(),
                cardReadDto.clientId(),
                cardReadDto.balance().add(amount),
                cardReadDto.createdDate(),
                cardReadDto.expireDate(),
                cardReadDto.status());
    }
}
