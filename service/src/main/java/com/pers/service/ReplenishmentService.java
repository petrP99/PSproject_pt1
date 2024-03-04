package com.pers.service;

import com.pers.dao.ReplenishmentRepository;
import com.pers.dto.ReplenishmentCreateDto;
import com.pers.dto.ReplenishmentReadDto;
import com.pers.mapper.ReplenishmentCreateMapper;
import com.pers.mapper.ReplenishmentReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ReplenishmentService {

    private final ReplenishmentRepository replenishmentRepository;
    private final ReplenishmentReadMapper replenishmentReadMapper;
    private final ReplenishmentCreateMapper replenishmentCreateMapper;

    public Long create(ReplenishmentCreateDto replenishmentDto) {
        var replenishmentEntity = replenishmentCreateMapper.mapFrom(replenishmentDto);
        return replenishmentRepository.save(replenishmentEntity).getId();
    }

    public Optional<ReplenishmentReadDto> findById(Long id) {
        return replenishmentRepository.findById(id).map(replenishmentReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var mayBeReplenishment = replenishmentRepository.findById(id);
        mayBeReplenishment.ifPresent(replenishment -> replenishmentRepository.delete(replenishment.getId()));
        return mayBeReplenishment.isPresent();
    }
    
}
