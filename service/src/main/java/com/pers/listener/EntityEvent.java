package com.pers.listener;

import org.springframework.context.ApplicationEvent;

public class EntityEvent extends ApplicationEvent {

    public EntityEvent(Object source) {
        super(source);
    }

}
