package com.pers.integration.service;

import com.pers.dto.PaymentCreateDto;
import com.pers.dto.TransferCreateDto;
import com.pers.dto.filter.PaymentFilterDto;
import com.pers.dto.filter.TransferFilterDto;
import com.pers.entity.Card;
import com.pers.entity.Client;
import com.pers.entity.Payment;
import com.pers.entity.Role;
import com.pers.entity.Status;
import com.pers.entity.Transfer;
import com.pers.entity.User;
import com.pers.integration.BaseIntegrationIT;
import com.pers.service.PaymentService;
import com.pers.service.TransferService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class TransferServiceIT extends BaseIntegrationIT {

    private final TransferService transferService;
    private Transfer transfer;
    private TransferCreateDto transferCreateDto;
    private TransferCreateDto transferCreateDto2;

    @BeforeEach
    void prepare() {
        var user = User.builder()
                .login("user10@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var user2 = User.builder()
                .login("user11@mail.ru")
                .password("123")
                .role(Role.USER)
                .build();

        var client = Client.builder()
                .user(user)
                .balance(new BigDecimal(0))
                .firstName("Petr")
                .lastName("Petrov")
                .phone("89632587854")
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var client2 = Client.builder()
                .user(user2)
                .balance(new BigDecimal(0))
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("89632557854")
                .createdTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        var card = Card.builder()
                .client(client)
                .balance(new BigDecimal(1000))
                .createdDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(5))
                .status(Status.ACTIVE)
                .build();

        var card2 = Card.builder()
                .client(client2)
                .balance(new BigDecimal(0))
                .createdDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(5))
                .status(Status.ACTIVE)
                .build();

        transfer = Transfer.builder()
                .clientId(client)
                .cardNoFrom(card)
                .cardNoTo(card2)
                .amount(new BigDecimal(0))
                .message("happy birthday")
                .recipient("Ivan Ivanov")
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        var transfer2 = Transfer.builder()
                .clientId(client2)
                .cardNoFrom(card2)
                .cardNoTo(card)
                .amount(new BigDecimal(250))
                .message("for you")
                .recipient("Ivan Ivanov")
                .timeOfTransfer(LocalDateTime.now())
                .status(Status.SUCCESS)
                .build();

        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(client);
        entityManager.persist(client2);
        entityManager.persist(card);
        entityManager.persist(card2);
        entityManager.persist(transfer);
        entityManager.persist(transfer2);

        transferCreateDto = new TransferCreateDto(
                transfer.getClientId().getId(),
                transfer.getCardNoFrom().getId(),
                transfer.getCardNoTo().getId(),
                transfer.getAmount(),
                transfer.getTimeOfTransfer(),
                transfer.getRecipient(),
                transfer.getMessage(),
                transfer.getStatus());

        transferCreateDto2 = new TransferCreateDto(
                transfer2.getClientId().getId(),
                transfer2.getCardNoFrom().getId(),
                transfer2.getCardNoTo().getId(),
                transfer2.getAmount(),
                transfer2.getTimeOfTransfer(),
                transfer2.getRecipient(),
                transfer2.getMessage(),
                transfer2.getStatus());
    }

    @Test
    void checkAndCreateTransfer() {
        var result = transferService.checkAndCreateTransfer(transferCreateDto2);

        assertFalse(result);
    }

    @Test
    void findById() {
        var result = transferService.findById(transfer.getId());

        assertThat(result).isPresent();
    }

    @Test
    void create() {
        var result = transferService.create(transferCreateDto);

        assertThat(result.recipient()).isEqualTo("Ivan Ivanov");
    }

    @Test
    void findAllByFilter() {
        var filter = new TransferFilterDto(null, null, null, null, null, null, null, "happy", null);
        var result = transferService.findAllByFilter(filter, Pageable.ofSize(1));

        assertThat(result).hasSize(1);
    }

    @Test
    void findAllByClientByFilter() {
        var filter = new TransferFilterDto(null, null, null, null, null, null, null, "happy", null);
        var result = transferService.findAllByClientByFilter(filter, Pageable.ofSize(1), transfer.getClientId().getId());

        assertThat(result).hasSize(1);
    }
}
