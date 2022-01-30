package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.gui.components.tables.BaseRow;
import com.intpfy.gui.dialogs.emi.DeleteEventDW;
import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class EventTableRow extends BaseRow {

    private final EventNameComponent name;
    private final LanguagesComponent languages;
    private final StartAndEndDateTimeComponent startAndEndDateTime;
    private final AccessTokensComponent accessTokens;
    private final AudienceAccessComponent audienceAccess;
    private final ActionsComponent actions;

    public EventTableRow(Event event, IParent parent) {
        super(event.getName(), parent, By.xpath(".//tr[./td[@data-title-text='Event name']//button[@title='" + event.getName() + "']]"));
        name = new EventNameComponent(this);
        languages = new LanguagesComponent(this);
        startAndEndDateTime = new StartAndEndDateTimeComponent(this);
        accessTokens = new AccessTokensComponent(this);
        audienceAccess = new AudienceAccessComponent(this);
        actions = new ActionsComponent(this);
    }

    public boolean isNameToDisplayActual(Event event, Duration timeout) {
        return name.isNameToDisplayActual(event, timeout);
    }

    public boolean areLanguagesActual(Event event, Duration timeout) {
        return languages.areActual(event, timeout);
    }

    public boolean isStartTimeActual(Event event, Duration timeout) {
        return startAndEndDateTime.isStartTimeActual(event, timeout);
    }

    public boolean isEndTimeActual(Event event, Duration timeout) {
        return startAndEndDateTime.isEndTimeActual(event, timeout);
    }

    public boolean isStartDateActual(Event event, Duration timeout) {
        return startAndEndDateTime.isStartDateActual(event, timeout);
    }

    public boolean isAudienceAccessActual(Event event, Duration timeout) {
        return audienceAccess.isActual(event, timeout);
    }

    public String getAudienceToken() {
        return accessTokens.getAudienceToken();
    }

    public String getInterpreterToken() {
        return accessTokens.getInterpreterToken();
    }

    public String getSpeakerToken() {
        return accessTokens.getSpeakerToken();
    }

    public String getModeratorToken() {
        return accessTokens.getModeratorToken();
    }

    public EditEventPage edit() {
        return actions.edit();
    }

    public EventArchivesDW openEventArchivesDW() {
        return actions.openEventArchivesDW();
    }

    public DeleteEventDW delete() {
        return actions.delete();
    }
}
