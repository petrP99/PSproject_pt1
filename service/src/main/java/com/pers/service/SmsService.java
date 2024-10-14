package com.pers.service;

import com.pers.dto.ClientReadDto;
import com.pers.dto.PaymentCreateDto;
import com.pers.dto.TransferCreateDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class SmsService {

    private final String URI = "https://api.iqsms.ru/messages/v2/send/?phone=%2B";
    private final String LOGIN = "f1728543122635";
    private final String PASSWORD = "246934";


    public void sendInfoPayment(PaymentCreateDto payment, ClientReadDto client, BigDecimal newBalance) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN, PASSWORD);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String resultUri = String.format("%s%s&text=\"%s\" codS %s. codeB %s",
                URI, client.getPhone(), payment.shopName(), payment.amount(), newBalance);
        ResponseEntity<String> exchange = restTemplate.exchange(resultUri, HttpMethod.GET, entity, String.class);
        System.out.println("exchange.getBody() = " + exchange.getBody());
        System.out.println("resultUri = " + resultUri);
    }

    public void sendInfoTransfer(TransferCreateDto transfer, ClientReadDto client, BigDecimal newBalance) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN, PASSWORD);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String resultUri = String.format("%s%s&text=from №%s codS %s, to %s, msg: %s. codeB %s",
                URI, client.getPhone(), transfer.cardIdFrom(), transfer.amount(), transfer.recipient(), transfer.message(), newBalance);
        ResponseEntity<String> exchange = restTemplate.exchange(resultUri, HttpMethod.GET, entity, String.class);
        System.out.println("exchange.getBody() = " + exchange.getBody());
        System.out.println("resultUri = " + resultUri);

    }
}
