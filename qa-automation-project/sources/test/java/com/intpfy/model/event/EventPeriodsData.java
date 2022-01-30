package com.intpfy.model.event;

import java.time.LocalDateTime;

public class EventPeriodsData {

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    public EventPeriodsData(LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }
}
