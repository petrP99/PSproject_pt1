package com.pers.mapper;

import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import com.pers.dto.ReplenishmentCreateDto;
import com.pers.entity.Replenishment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplenishmentCreateMapper implements Mapper<ReplenishmentCreateDto, Replenishment> {

    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;

    @Override
    public Replenishment mapFrom(ReplenishmentCreateDto object) {
        return Replenishment.builder()
                .clientTo(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findById(object.cardNo()).orElseThrow(IllegalArgumentException::new))
                .amount(object.amount())
                .timeOfReplenishment(object.timeOfReplenishment())
                .status(object.status())
                .build();
    }
}
