package com.intpfy.gui.components.emi.dashboard;

public enum StatsType {

    EVENTS("EVENTS"),
    USERS_PER_EVENT("USERS PER EVENT"),
    USERS_CONNECTED("USERS CONNECTED"),
    LANGUAGES("LANGUAGES"),
    MINUTES_CONNECTION_PER_USER("MINUTES CONNECTION PER USER");

    private final String text;

    StatsType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
