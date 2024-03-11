package com.pers.util;

import com.pers.entity.Card;
import com.pers.entity.Status;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class CheckBeforeAction {

    public static Status checkTransfer(Card cardFrom, Card cardTo, BigDecimal amount) {
        if (amount.compareTo(cardFrom.getBalance()) <= 0
            && cardFrom.getStatus() == Status.ACTIVE
            && cardTo.getStatus() == Status.ACTIVE) {
            cardTo.setBalance(cardTo.getBalance().add(amount));
            cardFrom.setBalance(cardFrom.getBalance().subtract(amount));

            return Status.SUCCESS;

        } else return Status.FAILED;
    }
}
