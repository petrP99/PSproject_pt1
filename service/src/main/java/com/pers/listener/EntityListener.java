package com.pers.listener;

import com.pers.repository.CardRepository;
import com.pers.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityListener {

    private final ClientService clientService;
    private final CardRepository cardRepository;

    @EventListener
    public void acceptEntity(EntityEvent event) {
        log.info("Вот тут происходит пополнение карты");
    }

}

