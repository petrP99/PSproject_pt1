package com.pers.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Service
public class GenerateAndCheckCodeService {

    private final int CODE_EXPIRE_TIME_SECOND = 60;
    private final LocalTime SEND_CODE_TIME = LocalTime.now();

    public int generateCode() {
        return nextInt(10000, 99999);
    }

    public boolean checkCodeByUser(int expectCode, int codeFromUser) {
        return expectCode == codeFromUser;
    }

}
