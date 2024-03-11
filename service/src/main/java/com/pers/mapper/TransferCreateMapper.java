package com.pers.mapper;

import com.pers.repository.CardRepository;
import com.pers.dto.TransferCreateDto;
import com.pers.entity.Transfer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferCreateMapper implements Mapper<TransferCreateDto, Transfer> {

    private final CardRepository cardRepository;

    @Override
    public Transfer mapFrom(TransferCreateDto object) {
        return Transfer.builder()
                .cardNoFrom(cardRepository.findById(object.cardNoFrom()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findById(object.cardNoTo()).orElseThrow(IllegalArgumentException::new))
                .timeOfTransfer(object.timeOfTransfer())
                .status(object.status())
                .build();
    }
}
