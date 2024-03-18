package com.pers.integration.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.Transfer;
import com.pers.entity.User;
import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import com.pers.repository.TransferRepository;
import com.pers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.CheckOfOperationUtil.*;
import static com.pers.util.CheckOfOperationUtil.checkTransfer;
import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class TransferIT extends BaseIntegrationIT {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;

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

        var amount = new BigDecimal(1000);
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
        updateClientBalance(client, cardRepository);
        updateClientBalance(client2, cardRepository);

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

        var amount = new BigDecimal(1000);
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
        updateClientBalance(client, cardRepository);
        updateClientBalance(client2, cardRepository);

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

        var amount = new BigDecimal(1000);
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
        transferRepository.delete(transfer);
        updateClientBalance(client, cardRepository);
        updateClientBalance(client2, cardRepository);

        var result = transferRepository.findById(transfer.getId());

        assertThat(result).isEmpty();
    }
}
