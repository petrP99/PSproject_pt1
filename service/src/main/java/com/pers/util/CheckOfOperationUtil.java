package com.pers.util;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Status;
import com.pers.repository.CardRepository;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class CheckOfOperationUtil {

    public static Status checkTransfer(Card cardFrom, Card cardTo, BigDecimal amount) {
        if (amount.compareTo(cardFrom.getBalance()) <= 0
                && cardFrom.getStatus() == Status.ACTIVE
                && cardTo.getStatus() == Status.ACTIVE) {
            cardTo.setBalance(cardTo.getBalance().add(amount));
            cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
            return Status.SUCCESS;
        } else {
            return Status.FAILED;
        }
    }

    public static Status checkPayment(Client client, Card card, BigDecimal amount) {
        if (client.getStatus() == Status.ACTIVE
                && card.getStatus() == Status.ACTIVE
                && amount.compareTo(card.getBalance()) <= 0) {
            card.setBalance(card.getBalance().subtract(amount));
            return Status.SUCCESS;
        } else {
            return Status.FAILED;
        }
    }

    public static Status checkReplenishment(Client client, Card card, BigDecimal amount) {
        if (client.getStatus() == Status.ACTIVE
                && card.getStatus() == Status.ACTIVE) {
            card.setBalance(card.getBalance().add(amount));
            return Status.SUCCESS;
        } else {
            return Status.FAILED;
        }
    }

    public BigDecimal updateClientBalance(Client client, CardRepository cardRepository) {
        return cardRepository.findById(client.getId()).stream()
                .map(Card::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
