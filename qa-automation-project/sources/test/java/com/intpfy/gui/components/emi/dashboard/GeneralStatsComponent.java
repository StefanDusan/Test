package com.intpfy.gui.components.emi.dashboard;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.intpfy.gui.components.emi.dashboard.StatsType.*;

public class GeneralStatsComponent extends BaseComponent {

    private final StatsContainerComponent eventsStats;
    private final StatsContainerComponent usersPerEventStats;
    private final StatsContainerComponent usersConnectedStats;
    private final StatsContainerComponent languagesStats;
    private final StatsContainerComponent minutesConnectionPerUserStats;

    public GeneralStatsComponent(IParent parent) {

        super("General Stats", parent, By.xpath(".//div[./div[@class = 'dashboard-stat-container']]"));

        eventsStats = new StatsContainerComponent(this, EVENTS);
        usersPerEventStats = new StatsContainerComponent(this, USERS_PER_EVENT);
        usersConnectedStats = new StatsContainerComponent(this, USERS_CONNECTED);
        languagesStats = new StatsContainerComponent(this, LANGUAGES);
        minutesConnectionPerUserStats = new StatsContainerComponent(this, MINUTES_CONNECTION_PER_USER);
    }

    public int getEventsCount() {
        return eventsStats.getCount();
    }

    public boolean isEventsCountNotEqual(int count, Duration timeout) {
        return eventsStats.isCountNotEqual(count, timeout);
    }

    public int getUsersPerEventCount() {
        return usersPerEventStats.getCount();
    }

    public boolean isUsersPerEventCountNotEqual(int count, Duration timeout) {
        return usersPerEventStats.isCountNotEqual(count, timeout);
    }

    public int getUsersConnectedCount() {
        return usersConnectedStats.getCount();
    }

    public boolean isUsersConnectedCountNotEqual(int count, Duration timeout) {
        return usersConnectedStats.isCountNotEqual(count, timeout);
    }

    public int getLanguagesCount() {
        return languagesStats.getCount();
    }

    public boolean isLanguagesCountNotEqual(int count, Duration timeout) {
        return languagesStats.isCountNotEqual(count, timeout);
    }

    public int getMinutesConnectionPerUserCount() {
        return minutesConnectionPerUserStats.getCount();
    }

    public boolean isMinutesConnectionPerUserCountNotEqual(int count, Duration timeout) {
        return minutesConnectionPerUserStats.isCountNotEqual(count, timeout);
    }
}
