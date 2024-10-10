package com.pers.service;

import com.pers.dto.ClientReadDto;
import com.pers.dto.PaymentCreateDto;
import com.pers.dto.TransferCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class SmsService {

    private final String URI = "https://api.iqsms.ru/messages/v2/send/?phone=%2B";
    private final String LOGIN = "f1728543122635";
    private final String PASSWORD = "246934";

    public void sendInfoPayment(PaymentCreateDto payment, ClientReadDto client, BigDecimal newBalance) {

        String resultUri = String.format("%s%s&text=Оплата \"%s\" на сумму %s. Баланс %s&login=%s&password=%s",
                URI, client.getPhone(), payment.shopName(), payment.amount(), newBalance, LOGIN, PASSWORD);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(resultUri, String.class);
    }

    public void sendInfoTransfer(TransferCreateDto transfer, ClientReadDto client, BigDecimal newBalance) {

    }


}
