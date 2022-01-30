package com.intpfy.model.event;

import java.util.Random;

public enum EventType {

    EventPro("Event Pro"),
    WebMeet("Connect (WebMeet)"),
    Classroom("Connect Pro (Classroom)");

    private final String type;

    EventType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static EventType getRandomEventType(){
        return values()[new Random().nextInt(values().length)];
    }
}