package com.pers.listener;

import com.pers.dto.ReplenishmentCreateDto;
import com.pers.repository.CardRepository;
import com.pers.service.ClientService;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

