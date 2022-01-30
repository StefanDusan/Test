package com.intpfy.gui.pages.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.GeneralInfoComponent;
import com.intpfy.model.Language;
import com.intpfy.model.event.EventType;
import com.intpfy.model.event.Location;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GeneralInfoPage extends BaseEventCreationPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//span[contains(@class, 'step-title') and text()=' Step 1 of 3']"))
    private Element pageTitle;

    private final GeneralInfoComponent generalInfo;

    public GeneralInfoPage(IPageContext pageContext) {
        super("General info page", pageContext);
        generalInfo = new GeneralInfoComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public void hideDelegatesStatus() {
        info("Hide Delegates status.");
        generalInfo.hideDelegatesStatus();
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

    public void setHostPassword(String password) {
        info(String.format("Set Host password '%s'.", password));
        generalInfo.setHostPassword(password);
    }

    public boolean isHostPasswordEqual(String password, Duration timeout) {
        return generalInfo.isHostPasswordEqual(password, timeout);
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

    public void selectLocation(Location location) {
        info(String.format("Select Location: '%s'.", location.getCityName()));
        generalInfo.selectLocation(location);
    }

    public ManagerAndModeratorPage clickContinue() {
        info("Proceed to 'Manager and Moderator' page.");
        continueButton.click();
        return new ManagerAndModeratorPage(getPageContext());
    }
}
