package com.intpfy.gui.components.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.settings.GeneralSettingsComponent;
import com.intpfy.model.Language;
import com.intpfy.model.event.EventType;
import com.intpfy.model.event.Location;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GeneralInfoComponent extends BaseComponent {

    private final GeneralSettingsComponent generalSettings;

    @ElementInfo(name = "Event name", findBy = @FindBy(name = "name"))
    private Input nameInput;

    private final LanguageComponent language;
    private final DateComponent date;
    private final TimeComponent time;
    private final LocationComponent location;

    public GeneralInfoComponent(IParent parent) {
        super("General info", parent, By.id("tab1"));
        generalSettings = new GeneralSettingsComponent(this);
        language = new LanguageComponent(this);
        date = new DateComponent(this);
        time = new TimeComponent(this);
        location = new LocationComponent(this);
    }

    public void hideDelegatesStatus() {
        generalSettings.hideDelegatesStatus();
    }

    public void selectEventType(EventType type) {
        generalSettings.selectEventType(type);
    }

    public void generateHostPassword() {
        generalSettings.generateHostPassword();
    }

    public String getHostPassword() {
        return generalSettings.getHostPassword();
    }

    public void setHostPassword(String password) {
        generalSettings.setHostPassword(password);
    }

    public boolean isHostPasswordEqual(String password, Duration timeout) {
        return generalSettings.isHostPasswordEqual(password, timeout);
    }

    public void setEventName(String name) {
        nameInput.setText(name);
    }

    public void selectLanguage(Language language) {
        this.language.select(language);
    }

    public void selectLanguages(List<Language> languages) {
        this.language.select(languages);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void selectDate(LocalDate date) {
        this.date.select(date);
    }

    public LocalTime getStartTime() {
        return time.getStart();
    }

    public void selectStartTime(LocalTime time) {
        this.time.setStart(time);
    }

    public LocalTime getEndTime() {
        return time.getEnd();
    }

    public void selectEndTime(LocalTime time) {
        this.time.setEnd(time);
    }

    public void selectLocation(Location location) {
        this.location.select(location);
    }
}
