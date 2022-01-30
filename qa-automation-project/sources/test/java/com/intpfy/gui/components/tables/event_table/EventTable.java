package com.intpfy.gui.components.tables.event_table;

import com.intpfy.gui.components.tables.BaseTable;
import com.intpfy.gui.components.tables.event_table.row.EventTableRow;
import com.intpfy.gui.dialogs.emi.DeleteEventDW;
import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class EventTable extends BaseTable {

    @ElementInfo(name = "Organization column title", findBy = @FindBy(xpath = ".//th//span[text()='Organization']"))
    private Element organizationColumnTitleElement;

    @ElementInfo(name = "Event name column title", findBy = @FindBy(xpath = ".//th//span[text()='Event name']"))
    private Element eventNameColumnTitleElement;

    @ElementInfo(name = "Languages column title", findBy = @FindBy(xpath = ".//th//span[text()='Languages']"))
    private Element languagesColumnTitleElement;

    @ElementInfo(name = "Event active column title", findBy = @FindBy(xpath = ".//th//span[text()='Event active']"))
    private Element eventActiveColumnTitleElement;

    @ElementInfo(name = "Start and End Time column title", findBy = @FindBy(xpath = ".//th//span[text()='Start and End Time']"))
    private Element startAndEndTimeColumnTitleElement;

    @ElementInfo(name = "Event location column title", findBy = @FindBy(xpath = ".//th//span[text()='Event location']"))
    private Element eventLocationColumnTitleElement;

    @ElementInfo(name = "Access tokens column title", findBy = @FindBy(xpath = ".//th//span[text()='Access tokens']"))
    private Element accessTokensColumnTitleElement;

    @ElementInfo(name = "Event Manager and Moderator column title", findBy = @FindBy(xpath = ".//th//span[text()='Event Manager and Moderator']"))
    private Element eventManagerAndModeratorColumnTitleElement;

    @ElementInfo(name = "Duration, Accumul. time, minutes column title", findBy = @FindBy(xpath = ".//th//span[text()='Duration, Accumul. time, minutes']"))
    private Element durationColumnTitleElement;

    @ElementInfo(name = "Current, Max users column title", findBy = @FindBy(xpath = ".//th//span[text()='Current, Max users']"))
    private Element currentMaxUsersColumnTitleElement;

    @ElementInfo(name = "Mobile access column title", findBy = @FindBy(xpath = ".//th//span[text()='Mobile access']"))
    private Element mobileAccessColumnTitleElement;

    @ElementInfo(name = "Audience access to Source column title", findBy = @FindBy(xpath = ".//th//span[text()='Audience access to Source']"))
    private Element audienceAccessToSourceColumnTitleElement;

    @ElementInfo(name = "Actions column title", findBy = @FindBy(xpath = ".//th//span[text()='Actions']"))
    private Element actionsColumnTitleElement;

    public EventTable(IParent parent) {
        super("Event table", parent, By.cssSelector("table.dataTable"));
    }

    public boolean isDisplayed(Event event, Duration timeout) {
        return createRow(event).visible(timeout);
    }

    public boolean isNotDisplayed(Event event, Duration timeout) {
        return createRow(event).notVisible(timeout);
    }

    public boolean isNameToDisplayActual(Event event, Duration timeout) {
        return createRow(event).isNameToDisplayActual(event, timeout);
    }

    public boolean areLanguagesActual(Event event, Duration timeout) {
        return createRow(event).areLanguagesActual(event, timeout);
    }

    public boolean isStartTimeActual(Event event, Duration timeout) {
        return createRow(event).isStartTimeActual(event, timeout);
    }

    public boolean isEndTimeActual(Event event, Duration timeout) {
        return createRow(event).isEndTimeActual(event, timeout);
    }

    public boolean isStartDateActual(Event event, Duration timeout) {
        return createRow(event).isStartDateActual(event, timeout);
    }

    public boolean isAudienceAccessActual(Event event, Duration timeout) {
        return createRow(event).isAudienceAccessActual(event, timeout);
    }

    public String getAudienceToken(Event event) {
        return createRow(event).getAudienceToken();
    }

    public String getInterpreterToken(Event event) {
        return createRow(event).getInterpreterToken();
    }

    public String getSpeakerToken(Event event) {
        return createRow(event).getSpeakerToken();
    }

    public String getModeratorToken(Event event) {
        return createRow(event).getModeratorToken();
    }

    public EditEventPage edit(Event event) {
        return createRow(event).edit();
    }

    public EventArchivesDW openEventArchivesDW(Event event) {
        return createRow(event).openEventArchivesDW();
    }

    public DeleteEventDW delete(Event event) {
        return createRow(event).delete();
    }

    public boolean isOrganizationColumnPresent(Duration timeout) {
        return organizationColumnTitleElement.visible(timeout);
    }

    public boolean isEventNameColumnPresent(Duration timeout) {
        return eventNameColumnTitleElement.visible(timeout);
    }

    public boolean isLanguagesColumnPresent(Duration timeout) {
        return languagesColumnTitleElement.visible(timeout);
    }

    public boolean isEventActiveColumnPresent(Duration timeout) {
        return eventActiveColumnTitleElement.visible(timeout);
    }

    public boolean isStartAndEndTimeColumnPresent(Duration timeout) {
        return startAndEndTimeColumnTitleElement.visible(timeout);
    }

    public boolean isEventLocationColumnPresent(Duration timeout) {
        return eventLocationColumnTitleElement.visible(timeout);
    }

    public boolean isAccessTokensColumnPresent(Duration timeout) {
        return accessTokensColumnTitleElement.visible(timeout);
    }

    public boolean isEventManagerAndModeratorColumnPresent(Duration timeout) {
        return eventManagerAndModeratorColumnTitleElement.visible(timeout);
    }

    public boolean isDurationColumnPresent(Duration timeout) {
        return durationColumnTitleElement.visible(timeout);
    }

    public boolean isCurrentMaxUsersColumnPresent(Duration timeout) {
        return currentMaxUsersColumnTitleElement.visible(timeout);
    }

    public boolean isMobileAccessColumnPresent(Duration timeout) {
        return mobileAccessColumnTitleElement.visible(timeout);
    }

    public boolean isAudienceAccessToSourceColumnPresent(Duration timeout) {
        return audienceAccessToSourceColumnTitleElement.visible(timeout);
    }

    public boolean isActionsColumnPresent(Duration timeout) {
        return actionsColumnTitleElement.visible(timeout);
    }

    private EventTableRow createRow(Event event) {
        return new EventTableRow(event, this);
    }
}
