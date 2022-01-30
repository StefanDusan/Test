package com.intpfy.model.event;

public enum Role {

    Audience("Audience"),
    Attendee("Attendees"),
    Interpreter("Interpreters"),
    Speaker("Speakers"),
    Delegate("Delegates"),
    Moderator("Moderators");

    private final String plural;

    Role(String plural) {
        this.plural = plural;
    }

    public static Role fromString(String roleString) {

        for (Role role : values()) {

            if (role.name().equalsIgnoreCase(roleString) || role.plural.equalsIgnoreCase(roleString)) {
                return role;
            }
        }

        throw new IllegalArgumentException(String.format("There is no Role for '%s'.", roleString));
    }
}
