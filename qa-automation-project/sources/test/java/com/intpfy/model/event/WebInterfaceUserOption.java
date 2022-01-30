package com.intpfy.model.event;

import java.util.Arrays;

public enum WebInterfaceUserOption {

    ON_SITE(null),
    ON_SITE_AND_WEB_MEET(EventType.EventPro),
    WEB_MEET(EventType.WebMeet),
    WEB_MEET_CLASSROOM(EventType.Classroom);

    private final EventType eventType;

    WebInterfaceUserOption(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType toEventType() {
        return eventType;
    }

    public static WebInterfaceUserOption fromEventType(EventType eventType) {
        return Arrays
                .stream(values())
                .filter(option -> option.eventType == eventType)
                .findAny()
                .orElseThrow();
    }
}
