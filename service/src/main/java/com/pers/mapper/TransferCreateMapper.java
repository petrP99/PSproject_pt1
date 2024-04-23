package com.pers.mapper;

import com.pers.dto.TransferCreateDto;
import static com.pers.entity.Status.FAILED;
import static com.pers.entity.Status.SUCCESS;
import com.pers.entity.Transfer;
import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

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
                .clientId(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .cardNoFrom(cardRepository.findById(object.cardIdFrom()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findById(object.cardIdTo()).orElseThrow(IllegalArgumentException::new))
                .amount(object.amount())
                .recipient(recipient)
                .message(object.message())
                .timeOfTransfer(Instant.now())
                .status(object.status() == null ? SUCCESS : FAILED)
                .build();
    }
}
