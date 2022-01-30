package com.intpfy.model.dashboard;

public class GeneralStats {

    private final int eventsCount;
    private final int usersPerEventCount;
    private final int usersConnectedCount;
    private final int languagesCount;
    private final int minutesConnectionPerUserCount;

    private GeneralStats(Builder builder) {
        this.eventsCount = builder.eventsCount;
        this.usersPerEventCount = builder.usersPerEventCount;
        this.usersConnectedCount = builder.usersConnectedCount;
        this.languagesCount = builder.languagesCount;
        this.minutesConnectionPerUserCount = builder.minutesConnectionPerUserCount;
    }

    @Override
    public String toString() {
        return "GeneralStats{" +
                "eventsCount=" + eventsCount +
                ", usersPerEventCount=" + usersPerEventCount +
                ", usersConnectedCount=" + usersConnectedCount +
                ", languagesCount=" + languagesCount +
                ", minutesConnectionPerUserCount=" + minutesConnectionPerUserCount +
                '}';
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public int getUsersPerEventCount() {
        return usersPerEventCount;
    }

    public int getUsersConnectedCount() {
        return usersConnectedCount;
    }

    public int getLanguagesCount() {
        return languagesCount;
    }

    public int getMinutesConnectionPerUserCount() {
        return minutesConnectionPerUserCount;
    }

    public static final class Builder {

        private int eventsCount;
        private int usersPerEventCount;
        private int usersConnectedCount;
        private int languagesCount;
        private int minutesConnectionPerUserCount;

        public Builder withEventsCount(int count) {
            this.eventsCount = count;
            return this;
        }

        public Builder withUsersPerEventCount(int count) {
            this.usersPerEventCount = count;
            return this;
        }

        public Builder withUsersConnectedCount(int count) {
            this.usersConnectedCount = count;
            return this;
        }

        public Builder withLanguagesCount(int count) {
            this.languagesCount = count;
            return this;
        }

        public Builder withMinutesConnectionPerUserCount(int count) {
            this.minutesConnectionPerUserCount = count;
            return this;
        }

        public GeneralStats build() {
            return new GeneralStats(this);
        }
    }
}
