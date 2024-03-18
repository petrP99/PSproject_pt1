package com.pers.integration.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import com.pers.repository.PaymentRepository;
import com.pers.repository.UserRepository;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.CheckOfOperationUtil.updateClientBalance;
import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class PaymentIT extends BaseIntegrationIT {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;

    @Test
    void createPayment() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .firstName("Ivan")
                .lastName("Ivanov")
                .balance(new BigDecimal(10000))
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var amount = new BigDecimal(1000);
        var status = CheckOfOperationUtil.checkPayment(client, card, amount);

        var payment = Payment.builder()
                .shopName("Apple")
                .amount(amount)
                .client(client)
                .card(card)
                .timeOfPay(LocalDateTime.now())
                .status(status)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        paymentRepository.save(payment);
        updateClientBalance(client, cardRepository);

        var result = paymentRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void findByClientIdAndCardNo() {
        var user = User.builder()
                .login("userEx1@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .firstName("Ivan")
                .lastName("Ivanov")
                .balance(new BigDecimal(10000))
                .createdTime(LocalDateTime.now())
                .status(Status.INACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var amount = new BigDecimal(1000);
        var status = CheckOfOperationUtil.checkPayment(client, card, amount);

        var payment = Payment.builder()
                .shopName("Apple")
                .amount(amount)
                .client(client)
                .card(card)
                .timeOfPay(LocalDateTime.now())
                .status(status)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        paymentRepository.save(payment);
        updateClientBalance(client, cardRepository);


        var result = paymentRepository.findByClientIdAndCardNo(client.getId(), card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
