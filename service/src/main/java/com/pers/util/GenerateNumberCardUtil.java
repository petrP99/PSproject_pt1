package com.pers.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class GenerateNumberCardUtil {
    Set<Long> numbersOfCards = new HashSet<>();

    public static Long generateNumber() {
        long number = (long) (Math.random() * 99660789L + 7733000000000000L);
        if (numbersOfCards.add(number)) {
            return number;
        } else {
            return generateNumber();
        }
    }
}
