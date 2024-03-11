package com.pers.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.Transfer;
import com.pers.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.CheckBeforeAction.checkTransfer;
import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferIT extends BaseTestRepositoryIT {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private CardRepository cardRepository;
    private TransferRepository transferRepository;

    @BeforeEach
    void openSession() {
        entityManager.getTransaction().begin();
        userRepository = context.getBean(UserRepository.class);
        clientRepository = context.getBean(ClientRepository.class);
        cardRepository = context.getBean(CardRepository.class);
        transferRepository = context.getBean(TransferRepository.class);
    }

    @Test
    void createTransfer() {
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
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var user2 = User.builder()
                .login("userEx2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .firstName("Petr")
                .lastName("Petrov")
                .balance(new BigDecimal(7400))
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .cardNo(generateNumber())
                .balance(new BigDecimal(1000))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        BigDecimal amount = new BigDecimal(1000);
        var status = checkTransfer(card2, card, amount);

        var transfer = Transfer.builder()
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(amount)
                .timeOfTransfer(LocalDateTime.now())
                .status(status)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        clientRepository.save(client);
        clientRepository.save(client2);
        cardRepository.save(card);
        cardRepository.save(card2);
        transferRepository.save(transfer);

        var result = transferRepository.findAll();
        var newCardBalance = cardRepository.findByCardId(card2.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(newCardBalance.get(0).getBalance()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void findByCardNoTo() {
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
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var user2 = User.builder()
                .login("userEx2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .firstName("Petr")
                .lastName("Petrov")
                .balance(new BigDecimal(7400))
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .cardNo(generateNumber())
                .balance(new BigDecimal(85241))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var transfer = Transfer.builder()
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(new BigDecimal(368))
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        clientRepository.save(client);
        clientRepository.save(client2);
        cardRepository.save(card);
        cardRepository.save(card2);
        transferRepository.save(transfer);

        var result = transferRepository.findByCardNoTo(card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    void deleteTransfer() {
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
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(777))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var user2 = User.builder()
                .login("userEx2@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .firstName("Petr")
                .lastName("Petrov")
                .balance(new BigDecimal(7400))
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .cardNo(generateNumber())
                .balance(new BigDecimal(1000))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        BigDecimal amount = new BigDecimal(1000);
        card.setBalance(card.getBalance().add(amount));
        card2.setBalance(card2.getBalance().subtract(amount));

        var transfer = Transfer.builder()
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(amount)
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        clientRepository.save(client);
        clientRepository.save(client2);
        cardRepository.save(card);
        cardRepository.save(card2);
        transferRepository.save(transfer);
        transferRepository.delete(transfer);
        var result = transferRepository.findById(transfer.getId());

        assertThat(result).isEmpty();
    }
}
