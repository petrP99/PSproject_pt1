package com.pers.mapper;

import com.pers.entity.Client;
import com.pers.repository.CardRepository;
import com.pers.dto.TransferCreateDto;
import com.pers.entity.Transfer;
import com.pers.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferCreateMapper implements Mapper<TransferCreateDto, Transfer> {

    private final CardRepository cardRepository;

    @Override
    public Transfer mapFrom(TransferCreateDto object) {
        return Transfer.builder()
                .cardNoFrom(cardRepository.findByCardNoFrom(object.cardNoFrom()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findByCardNoTo(object.cardNoTo()).orElseThrow(IllegalArgumentException::new))
                .recipient(object.recipient())
                .message(object.message())
                .timeOfTransfer(object.timeOfTransfer())
                .status(object.status())
                .build();
    }
}
