package com.pers.integration.repository;

import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Replenishment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.User;
import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import com.pers.repository.ReplenishmentRepository;
import com.pers.repository.UserRepository;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pers.util.CheckOfOperationUtil.updateClientBalance;
import static com.pers.util.ExpireDateUtil.calculateExpireDate;
import static com.pers.util.GenerateNumberCardUtil.generateNumber;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReplenishmentIT extends BaseIntegrationIT {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;
    private final ReplenishmentRepository replenishmentRepository;

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

        var amount = new BigDecimal(753);
        var amount2 = new BigDecimal(900);
        var status = CheckOfOperationUtil.checkReplenishment(client, card, amount);

        var replenishment = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(amount)
                .timeOfReplenishment(LocalDateTime.now())
                .status(status)
                .build();

        var replenishment2 = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(amount2)
                .timeOfReplenishment(LocalDateTime.now())
                .status(status)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        replenishmentRepository.save(replenishment);
        replenishmentRepository.save(replenishment2);
        updateClientBalance(client, cardRepository);

        var result = replenishmentRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
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

        var card2 = Card.builder()
                .client(client)
                .cardNo(generateNumber())
                .balance(new BigDecimal(1))
                .createdDate(LocalDate.now())
                .expireDate(calculateExpireDate())
                .status(Status.ACTIVE)
                .build();

        var amount = new BigDecimal(753);
        var amount2 = new BigDecimal(900);
        var status = CheckOfOperationUtil.checkReplenishment(client, card, amount);

        var replenishment = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card)
                .amount(amount)
                .timeOfReplenishment(LocalDateTime.now())
                .status(status)
                .build();

        var replenishment2 = Replenishment.builder()
                .clientTo(client)
                .cardNoTo(card2)
                .amount(amount2)
                .timeOfReplenishment(LocalDateTime.now())
                .status(status)
                .build();

        userRepository.save(user);
        clientRepository.save(client);
        cardRepository.save(card);
        cardRepository.save(card2);
        replenishmentRepository.save(replenishment);
        replenishmentRepository.save(replenishment2);
        updateClientBalance(client, cardRepository);

        var result = replenishmentRepository.findByClientIdAndCardNo(client.getId(), card.getCardNo());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}
