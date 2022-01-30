package com.intpfy.gui.pages.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.GeneralInfoComponent;
import com.intpfy.gui.components.emi.event_creation.ManagerAndModeratorComponent;
import com.intpfy.gui.components.emi.event_creation.TokensAndNameCustomizationComponent;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.EventType;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EditEventPage extends BaseEventCreationPage {

    @ElementInfo(name = "Add event page title", findBy = @FindBy(xpath = "//span[text() = 'Edit event']"))
    private Element pageTitle;

    private final GeneralInfoComponent generalInfo;
    private final ManagerAndModeratorComponent managerAndModerator;
    private final TokensAndNameCustomizationComponent tokensAndNameCustomization;

    public EditEventPage(IPageContext pageContext) {
        super("Edit Event page", pageContext);
        generalInfo = new GeneralInfoComponent(this);
        managerAndModerator = new ManagerAndModeratorComponent(this);
        tokensAndNameCustomization = new TokensAndNameCustomizationComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public void selectEventType(EventType type) {
        info(String.format("Select Event type: '%s'.", type.name()));
        generalInfo.selectEventType(type);
    }

    public void generateHostPassword() {
        info("Generate Host password.");
        generalInfo.generateHostPassword();
    }

    public String getHostPassword() {
        return generalInfo.getHostPassword();
    }

    public void setEventName(String name) {
        info(String.format("Set Event name: '%s'.", name));
        generalInfo.setEventName(name);
    }

    public void selectLanguage(Language language) {
        info(String.format("Select Language: '%s'.", language));
        generalInfo.selectLanguage(language);
    }

    public void selectLanguages(List<Language> languages) {
        info(String.format("Select Languages: '%s'.", languages));
        generalInfo.selectLanguages(languages);
    }

    public LocalDate getDate() {
        return generalInfo.getDate();
    }

    public void selectDate(LocalDate date) {
        info(String.format("Select Date: '%s'.", date));
        generalInfo.selectDate(date);
    }

    public LocalTime getStartTime() {
        return generalInfo.getStartTime();
    }

    public void selectStartTime(LocalTime time) {
        info(String.format("Select start Time: '%s'.", time));
        generalInfo.selectStartTime(time);
    }

    public LocalTime getEndTime() {
        return generalInfo.getEndTime();
    }

    public void selectEndTime(LocalTime time) {
        info(String.format("Select End Time: '%s'.", time));
        generalInfo.selectEndTime(time);
    }

    public void setNameToDisplay(String displayName) {
        info(String.format("Set event name to display: '%s'.", displayName));
        tokensAndNameCustomization.setNameToDisplay(displayName);
    }

    public String getAudienceToken() {
        return tokensAndNameCustomization.getAudienceToken();
    }

    public String getSpeakerToken() {
        return tokensAndNameCustomization.getSpeakerToken();
    }

    public String getInterpreterToken() {
        return tokensAndNameCustomization.getInterpreterToken();
    }

    public String getModeratorToken() {
        return tokensAndNameCustomization.getModeratorToken();
    }

    public AccessChangeDW blockAudienceAccess() {
        info("Block Audience access.");
        return tokensAndNameCustomization.blockAudienceAccess();
    }

    public void enablePreCallTestForAudience() {
        info("Enable Pre call test for Audience.");
        tokensAndNameCustomization.enablePreCallTestForAudience();
    }

    public boolean isPreCallTestEnabledForAudience(Duration timeout) {
        return tokensAndNameCustomization.isPreCallTestEnabledForAudience(timeout);
    }

    public void enablePreCallTestForInterpreterAndSpeaker() {
        info("Enable Pre call test for Interpreter and Speaker.");
        tokensAndNameCustomization.enablePreCallTestForInterpreterAndSpeaker();
    }

    public boolean isPreCallTestEnabledForInterpreterAndSpeaker(Duration timeout) {
        return tokensAndNameCustomization.isPreCallTestEnabledForInterpreterAndSpeaker(timeout);
    }

    public EventsPage save() {
        info("Save Event.");
        continueButton.click();
        return new EventsPage(getPageContext());
    }
}
