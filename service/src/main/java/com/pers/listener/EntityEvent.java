package com.pers.listener;

import com.pers.dto.ReplenishmentCreateDto;
import org.springframework.context.ApplicationEvent;

public class EntityEvent extends ApplicationEvent {

    public EntityEvent(ReplenishmentCreateDto replenishmentDto) {
        super(replenishmentDto);
    }

}
