package com.pers.mapper;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.entity.Replenishment;
import static com.pers.entity.Status.FAILED;
import static com.pers.entity.Status.SUCCESS;
import com.pers.repository.CardRepository;
import com.pers.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ReplenishmentCreateMapper implements Mapper<ReplenishmentCreateDto, Replenishment> {

    private final ClientRepository clientRepository;
    private final CardRepository cardRepository;

    @Override
    public Replenishment mapFrom(ReplenishmentCreateDto object) {
        return Replenishment.builder()
                .clientTo(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .cardNoTo(cardRepository.findById(object.cardId()).orElseThrow(IllegalArgumentException::new))
                .amount(object.amount())
                .timeOfReplenishment(Instant.now())
                .status(object.status() == null ? SUCCESS : FAILED)
                .build();
    }
}
