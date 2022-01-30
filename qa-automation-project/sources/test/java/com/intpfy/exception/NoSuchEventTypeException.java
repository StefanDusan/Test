package com.intpfy.exception;

import com.intpfy.model.event.EventType;

public class NoSuchEventTypeException extends IllegalArgumentException {

    public NoSuchEventTypeException(EventType eventType) {
        super(String.format("No such Event type '%s'.", eventType));
    }
}
