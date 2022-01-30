package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.LocalTime;

public class TimeComponent extends BaseComponent {

    @ElementInfo(name = "Start hours", findBy =
    @FindBy(css = "div[ng-model='period.dateStart'] input[ng-model='hours']"))
    private Input startHoursInput;

    @ElementInfo(name = "Start minutes", findBy =
    @FindBy(css = "div[ng-model='period.dateStart'] input[ng-model='minutes']"))
    private Input startMinutesInput;

    @ElementInfo(name = "End hours", findBy =
    @FindBy(css = "div[ng-model='period.dateEnd'] input[ng-model='hours']"))
    private Input endHoursInput;

    @ElementInfo(name = "End minutes", findBy =
    @FindBy(css = "div[ng-model='period.dateEnd'] input[ng-model='minutes']"))
    private Input endMinutesInput;

    public TimeComponent(IParent parent) {
        super("Time", parent, By.cssSelector("div.time-periods"));
    }

    public LocalTime getStart() {
        return LocalTime.of(getStartHours(), getStartMinutes());
    }

    public void setStart(LocalTime time) {
        setStartHours(time.getHour());
        setStartMinutes(time.getMinute());
    }

    public LocalTime getEnd() {
        return LocalTime.of(getEndHours(), getEndMinutes());
    }

    public void setEnd(LocalTime time) {
        setEndHours(time.getHour());
        setEndMinutes(time.getMinute());
    }

    private int getStartHours() {
        return Integer.parseInt(startHoursInput.getText());
    }

    private void setStartHours(int hours) {
        startHoursInput.setText(hours);
    }

    private int getStartMinutes() {
        return Integer.parseInt(startMinutesInput.getText());
    }

    private void setStartMinutes(int minutes) {
        startMinutesInput.setText(minutes);
    }

    private int getEndHours() {
        return Integer.parseInt(endHoursInput.getText());
    }

    private void setEndHours(int hours) {
        endHoursInput.setText(hours);
    }

    private int getEndMinutes() {
        return Integer.parseInt(endMinutesInput.getText());
    }

    private void setEndMinutes(int minutes) {
        endMinutesInput.setText(minutes);
    }
}
