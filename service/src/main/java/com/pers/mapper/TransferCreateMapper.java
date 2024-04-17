package com.pers.mapper;

import static com.pers.entity.Status.FAILED;
import com.pers.repository.CardRepository;
import com.pers.dto.TransferCreateDto;
import com.pers.entity.Transfer;
import com.pers.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.pers.entity.Status.SUCCESS;

@Component
@RequiredArgsConstructor
public class TransferCreateMapper implements Mapper<TransferCreateDto, Transfer> {

    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;

    @Override
    public Transfer mapFrom(TransferCreateDto object) {
        var card = cardRepository.findById(object.cardIdTo()).orElseThrow();
        var recipientClientId = card.getClient().getId();
        var recipient = clientRepository.findFirstAndLastNameByClientId(recipientClientId);

        return Transfer.builder()
                .cardNoFrom(cardRepository.findById(object.cardIdFrom()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findById(object.cardIdTo()).orElseThrow(IllegalArgumentException::new))
                .amount(object.amount())
                .recipient(recipient)
                .message(object.message())
                .timeOfTransfer(LocalDateTime.now())
                .status(object.status() == null ? SUCCESS : FAILED)
                .build();
    }
}
