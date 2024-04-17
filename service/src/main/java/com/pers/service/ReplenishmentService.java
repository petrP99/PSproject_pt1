package com.pers.service;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.dto.ReplenishmentReadDto;
import com.pers.dto.filter.ReplenishmentFilterDto;
import com.pers.entity.Status;
import static com.pers.entity.Status.FAILED;
import com.pers.mapper.ReplenishmentCreateMapper;
import com.pers.mapper.ReplenishmentReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.ReplenishmentRepository;
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
public class ReplenishmentService {

    private final ReplenishmentRepository replenishmentRepository;
    private final ReplenishmentReadMapper replenishmentReadMapper;
    private final ReplenishmentCreateMapper replenishmentCreateMapper;
    private final ClientService clientService;
    private final CardService cardService;
    private final CardRepository cardRepository;

    @Transactional
    public String checkReplenishment(ReplenishmentCreateDto replenishment) {

        var clientId = replenishment.clientId();
        var clientReadDto = clientService.findById(clientId).orElseThrow();
        var cardReadDto = cardService.findById(replenishment.cardId()).orElseThrow();
        var amount = replenishment.amount();

        if (clientReadDto.getStatus() == Status.ACTIVE && cardReadDto.status() == Status.ACTIVE) {
            create(replenishment);
            var cardCreateDto = CheckOfOperationUtil.cardUpdateBalanceAdd(cardReadDto, amount);
            cardService.updateBalance(cardCreateDto);
            var newBalance = CheckOfOperationUtil.updateClientBalance(replenishment.clientId(), cardRepository);
            var clientUpdateBalanceDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientReadDto, newBalance);
            clientService.updateBalance(clientUpdateBalanceDto);
        } else {
            var replenishmentFail = new ReplenishmentCreateDto(
                    replenishment.clientId(),
                    replenishment.cardId(),
                    replenishment.amount(),
                    replenishment.timeOfReplenishment(),
                    FAILED);
            create(replenishmentFail);

            return "replenishment/fail";
        }
        return "replenishment/success";
    }

    @Transactional
    public ReplenishmentReadDto create(ReplenishmentCreateDto replenishmentDto) {
        return Optional.of(replenishmentDto)
                .map(replenishmentCreateMapper::mapFrom)
                .map(replenishmentRepository::save)
                .map(replenishmentReadMapper::mapFrom)
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

    @Transactional
    public Optional<ReplenishmentReadDto> update(Long id, ReplenishmentCreateDto replenishmentDto) {
        return replenishmentRepository.findById(id)
                .map(entity -> replenishmentCreateMapper.map(replenishmentDto, entity))
                .map(replenishmentRepository::saveAndFlush)
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
}