package com.intpfy.exception;

public class EventTypeNotDefinedException extends IllegalStateException {

    public EventTypeNotDefinedException() {
        super("Can't define Event type.");
    }
}
