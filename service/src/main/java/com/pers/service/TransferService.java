package com.pers.service;

import com.pers.dao.TransferRepository;
import com.pers.dto.TransferCreateDto;
import com.pers.dto.TransferReadDto;
import com.pers.mapper.TransferCreateMapper;
import com.pers.mapper.TransferReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final TransferReadMapper transferReadMapper;
    private final TransferCreateMapper transferCreateMapper;

    public Long create(TransferCreateDto transferDto) {
        var transferEntity = transferCreateMapper.mapFrom(transferDto);
        return transferRepository.save(transferEntity).getId();
    }

    public Optional<TransferReadDto> findById(Long id) {
        return transferRepository.findById(id).map(transferReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBeTransfer = transferRepository.findById(id);
        mayBeTransfer.ifPresent(transfer -> transferRepository.delete(transfer.getId()));
        return mayBeTransfer.isPresent();
    }
}
