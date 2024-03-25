package com.pers.mapper;

import com.pers.dto.TransferReadDto;
import com.pers.entity.Transfer;

public class TransferReadMapper implements Mapper<Transfer, TransferReadDto> {
    @Override
    public TransferReadDto mapFrom(Transfer object) {
        return new TransferReadDto(
                object.getId(),
                object.getCardNoFrom().getCardNo(),
                object.getCardNoTo().getCardNo(),
                object.getAmount(),
                object.getTimeOfTransfer(),
                object.getRecipient(),
                object.getMessage(),
                object.getStatus()
        );
    }
}
