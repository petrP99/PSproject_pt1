package com.pers.service;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.dto.ReplenishmentReadDto;
import com.pers.dto.filter.ReplenishmentFilterDto;
import com.pers.entity.Status;
import com.pers.listener.EntityEvent;
import com.pers.mapper.ReplenishmentCreateMapper;
import com.pers.mapper.ReplenishmentReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.ReplenishmentRepository;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

import static com.pers.entity.Status.FAILED;


@Service
@Transactional
@RequiredArgsConstructor
public class ReplenishmentService {

    private final ReplenishmentRepository replenishmentRepository;
    private final ReplenishmentReadMapper replenishmentReadMapper;
    private final ReplenishmentCreateMapper replenishmentCreateMapper;
    private final ClientService clientService;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final File file = new File("Replenishment history.txt");


    @Transactional
    public boolean checkAndCreateReplenishment(ReplenishmentCreateDto replenishment) {
        var clientReadDto = clientService.findById(replenishment.clientId()).orElseThrow();
        var cardReadDto = cardService.findById(replenishment.cardId()).orElseThrow();

        if (clientReadDto.getStatus() == Status.ACTIVE && cardReadDto.status() == Status.ACTIVE) {
            var replenishmentReadDto = create(replenishment);
            var cardCreateDto = CheckOfOperationUtil.createDtoCardUpdateBalanceAdd(cardReadDto, replenishment.amount());
            cardService.updateCardBalance(cardCreateDto);
            var newBalance = CheckOfOperationUtil.calculateClientBalance(cardRepository.findByClientId(replenishment.clientId()));
            var clientUpdateBalanceDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientReadDto, newBalance);
            clientService.updateBalance(clientUpdateBalanceDto);
            writeHistoryPayments(replenishmentReadDto);
        } else {
            var replenishmentFail = new ReplenishmentCreateDto(
                    replenishment.clientId(),
                    replenishment.cardId(),
                    replenishment.amount(),
                    replenishment.timeOfReplenishment(),
                    FAILED);
            writeHistoryPayments(create(replenishmentFail));
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Transactional
    public ReplenishmentReadDto create(ReplenishmentCreateDto replenishmentDto) {
        return Optional.of(replenishmentDto)
                .map(replenishmentCreateMapper::mapFrom)
                .map(replenishmentRepository::save)
                .map(it -> {
                    eventPublisher.publishEvent(new EntityEvent(replenishmentDto));
                    return replenishmentReadMapper.mapFrom(it);
                })
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return replenishmentRepository.findById(id)
                .map(entity -> {
                    replenishmentRepository.delete(entity);
                    replenishmentRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Page<ReplenishmentReadDto> findAllByFilter(ReplenishmentFilterDto filter, Pageable pageable) {
        return replenishmentRepository.findAllByFilter(filter, pageable)
                .map(replenishmentReadMapper::mapFrom);
    }

    public Page<ReplenishmentReadDto> findByClientByFilter(ReplenishmentFilterDto filter, Pageable pageable, Long clientId) {
        return replenishmentRepository.findAllByClientByFilter(filter, pageable, clientId)
                .map(replenishmentReadMapper::mapFrom);
    }


    public Optional<ReplenishmentReadDto> findById(Long id) {
        return replenishmentRepository.findById(id)
                .map(replenishmentReadMapper::mapFrom);
    }

    public List<ReplenishmentReadDto> findByClientId(Long id) {
        return replenishmentRepository.findAllByClientToId(id).stream()
                .map(replenishmentReadMapper::mapFrom)
                .toList();

    }

    @SneakyThrows
    public void writeHistoryPayments(ReplenishmentReadDto replenishmentDto) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.newLine();
        writer.write(String.format("card №\tamount\ttime\tstatus\n" +
                        "%s\t%s\t%s\t%s\n",
                replenishmentDto.cardNo(),
                replenishmentDto.amount(),
                replenishmentDto.timeOfReplenishment(),
                replenishmentDto.status()));
        writer.close();
    }
}