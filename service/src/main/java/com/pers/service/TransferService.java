package com.pers.service;

import com.pers.dto.TransferCreateDto;
import com.pers.dto.TransferReadDto;
import com.pers.dto.filter.TransferFilterDto;
import com.pers.entity.Status;
import static com.pers.entity.Status.FAILED;
import com.pers.mapper.TransferCreateMapper;
import com.pers.mapper.TransferReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.TransferRepository;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferReadMapper transferReadMapper;
    private final TransferCreateMapper transferCreateMapper;
    private final ClientService clientService;
    private final CardService cardService;
    private final CardRepository cardRepository;

    @Transactional
    public String checkTransfer(TransferCreateDto transfer) {

        var cardFrom = cardService.findById(transfer.cardIdFrom()).orElseThrow();
        var cardTo = cardService.findById(transfer.cardIdTo()).orElseThrow();
        var clientFrom = clientService.findById(cardFrom.clientId()).orElseThrow();
        var clientTo = clientService.findById(cardTo.clientId()).orElseThrow();
        var amount = transfer.amount();

        if (clientTo.getStatus() == Status.ACTIVE
            && cardTo.status() == Status.ACTIVE
            && cardFrom.status() == Status.ACTIVE
            && amount.compareTo(cardFrom.balance()) <= 0) {

            create(transfer);

            var cardFromUpdateBalance = CheckOfOperationUtil.cardUpdateBalanceSubtract(cardFrom, amount);
            var cardToUpdateBalance = CheckOfOperationUtil.cardUpdateBalanceAdd(cardTo, amount);
            cardService.updateBalance(cardFromUpdateBalance);
            cardService.updateBalance(cardToUpdateBalance);

            var clientFromNewBalance = CheckOfOperationUtil.updateClientBalance(clientFrom.getId(), cardRepository);
            var clientToNewBalance = CheckOfOperationUtil.updateClientBalance(clientTo.getId(), cardRepository);
            var clientFromUpdateDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientFrom, clientFromNewBalance);
            var clientToUpdateDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientTo, clientToNewBalance);

            clientService.updateBalance(clientFromUpdateDto);
            clientService.updateBalance(clientToUpdateDto);
        } else {
            var transferFail = new TransferCreateDto(
                    transfer.cardIdFrom(),
                    transfer.cardIdTo(),
                    transfer.amount(),
                    transfer.timeOfTransfer(),
                    transfer.recipient(),
                    transfer.message(),
                    FAILED);

            create(transferFail);

            return "transfer/fail";
        }
        return "transfer/success";
    }

    @Transactional
    public TransferReadDto create(TransferCreateDto transferDto) {
        return Optional.of(transferDto)
                .map(transferCreateMapper::mapFrom)
                .map(transferRepository::save)
                .map(transferReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return transferRepository.findById(id)
                .map(entity -> {
                    transferRepository.delete(entity);
                    transferRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public List<TransferReadDto> findBySenderClientId(Long clientId) {
        return transferRepository.findAllByCardNoFromClientId(clientId).stream()
                .map(transferReadMapper::mapFrom)
                .toList();
    }

    public Page<TransferReadDto> findAllByFilter(TransferFilterDto filter, Pageable pageable) {
        return transferRepository.findAllByFilter(filter, pageable)
                .map(transferReadMapper::mapFrom);
    }

    @Transactional
    public Optional<TransferReadDto> update(Long id, TransferCreateDto transferDto) {
        return transferRepository.findById(id)
                .map(entity -> transferCreateMapper.map(transferDto, entity))
                .map(transferRepository::saveAndFlush)
                .map(transferReadMapper::mapFrom);
    }

    public Optional<TransferReadDto> findById(Long id) {
        return transferRepository.findById(id)
                .map(transferReadMapper::mapFrom);
    }

}
