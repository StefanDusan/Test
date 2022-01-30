package com.intpfy.gui.pages.emi;

import com.intpfy.gui.components.tables.event_table.EventTable;
import com.intpfy.gui.dialogs.emi.DeleteEventDW;
import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Input;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class EventsPage extends BaseEmiPage {

    @ElementInfo(name = "Search label", findBy = @FindBy(xpath = "//small[contains(text(),'(search an event)')]"))
    private Element searchLabel;

    @ElementInfo(name = "Search", findBy = @FindBy(css = "div.form-group input[type='search']"))
    private Input searchInput;

    @ElementInfo(name = "Reset filter", findBy = @FindBy(xpath = "//button[text() = 'Reset filter']"))
    private Button resetFilterButton;

    private final EventTable table;

    public EventsPage(IPageContext pageContext) {
        super("Events page", pageContext);
        table = new EventTable(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return searchLabel.visible(timeout);
    }

    public boolean exists(Event event) {
        searchIfNotDisplayed(event);
        return isDisplayed(event, AJAX_TIMEOUT);
    }

    public boolean isNameToDisplayActual(Event event) {
        searchIfNotDisplayed(event);
        return table.isNameToDisplayActual(event, AJAX_TIMEOUT);
    }

    public boolean areLanguagesActual(Event event) {
        searchIfNotDisplayed(event);
        return table.areLanguagesActual(event, AJAX_TIMEOUT);
    }

    public boolean isStartTimeActual(Event event) {
        searchIfNotDisplayed(event);
        return table.isStartTimeActual(event, AJAX_TIMEOUT);
    }

    public boolean isEndTimeActual(Event event) {
        searchIfNotDisplayed(event);
        return table.isEndTimeActual(event, AJAX_TIMEOUT);
    }

    public boolean isStartDateActual(Event event) {
        searchIfNotDisplayed(event);
        return table.isStartDateActual(event, AJAX_TIMEOUT);
    }

    public boolean isAudienceAccessActual(Event event) {
        searchIfNotDisplayed(event);
        return table.isAudienceAccessActual(event, AJAX_TIMEOUT);
    }

    public String getAudienceToken(Event event) {
        searchIfNotDisplayed(event);
        return table.getAudienceToken(event);
    }

    public String getInterpreterToken(Event event) {
        searchIfNotDisplayed(event);
        return table.getInterpreterToken(event);
    }

    public String getSpeakerToken(Event event) {
        searchIfNotDisplayed(event);
        return table.getSpeakerToken(event);
    }

    public String getModeratorToken(Event event) {
        searchIfNotDisplayed(event);
        return table.getModeratorToken(event);
    }

    public EventArchivesDW openEventArchives(Event event) {
        info(String.format("Open 'Event archives' dialog window for event '%s'.", event));
        searchIfNotDisplayed(event);
        return table.openEventArchivesDW(event);
    }

    public EditEventPage edit(Event event) {
        info(String.format("Edit event '%s'.", event));
        searchIfNotDisplayed(event);
        return table.edit(event);
    }

    public EventsPage delete(Event event) {
        info(String.format("Delete event '%s'.", event));
        if (exists(event)) {
            DeleteEventDW deleteEventDW = table.delete(event);
            deleteEventDW.assertIsOpened();
            deleteEventDW.confirm();
            deleteEventDW.close();
            deleteEventDW.assertNotVisible();
        }
        return new EventsPage(getPageContext());
    }

    public void resetFilter() {
        info("Reset filter.");
        resetFilterButton.click();
    }

    public void search(Event event) {
        info(String.format("Search event '%s'.", event));
        searchInput.setText(event.getName());
        searchInput.sendKeys(Keys.ENTER);
        isDisplayed(event, AJAX_TIMEOUT);
    }

    public boolean isOrganizationTableColumnPresent() {
        return table.isOrganizationColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isEventNameTableColumnPresent() {
        return table.isEventNameColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isLanguagesTableColumnPresent() {
        return table.isLanguagesColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isEventActiveTableColumnPresent() {
        return table.isEventActiveColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isStartAndEndTimeTableColumnPresent() {
        return table.isStartAndEndTimeColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isEventLocationTableColumnPresent() {
        return table.isEventLocationColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isAccessTokensTableColumnPresent() {
        return table.isAccessTokensColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isEventManagerAndModeratorTableColumnPresent() {
        return table.isEventManagerAndModeratorColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isDurationTableColumnPresent() {
        return table.isDurationColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isCurrentMaxUsersTableColumnPresent() {
        return table.isCurrentMaxUsersColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isMobileAccessTableColumnPresent() {
        return table.isMobileAccessColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isAudienceAccessToSourceTableColumnPresent() {
        return table.isAudienceAccessToSourceColumnPresent(AJAX_TIMEOUT);
    }

    public boolean isActionsTableColumnPresent() {
        return table.isActionsColumnPresent(AJAX_TIMEOUT);
    }

    private void searchIfNotDisplayed(Event event) {
        if (isNotDisplayed(event, Duration.ZERO)) {
            search(event);
        }
    }

    private boolean isDisplayed(Event event, Duration timeout) {
        return table.isDisplayed(event, timeout);
    }

    private boolean isNotDisplayed(Event event, Duration timeout) {
        return table.isNotDisplayed(event, timeout);
    }
}
