package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentIT extends BaseTestRepositoryIT {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private CardRepository cardRepository;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void openSession() {
        entityManager.getTransaction().begin();
        userRepository = context.getBean(UserRepository.class);
        clientRepository = context.getBean(ClientRepository.class);
        cardRepository = context.getBean(CardRepository.class);
        paymentRepository = context.getBean(PaymentRepository.class);
    }

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

        var payment = Payment.builder()
                .shopName("Apple")
                .amount(new BigDecimal(152.4))
                .client(client)
                .card(card)
                .timeOfPay(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        paymentRepository.save(payment);

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

        var payment = Payment.builder()
                .shopName("Apple")
                .amount(new BigDecimal(152.4))
                .client(client)
                .card(card)
                .timeOfPay(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        paymentRepository.save(payment);

        var result = paymentRepository.findByClientIdAndCardNo(client.getId(), card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
