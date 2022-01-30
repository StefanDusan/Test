package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Savable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class CountdownTimerDW extends BaseComponent implements Closeable, Savable, Cancelable {

    @ElementInfo(name = "Hours", findBy = @FindBy(css = "td.hours input"))
    private Input hoursInput;

    @ElementInfo(name = "Minutes", findBy = @FindBy(css = "td.minutes input"))
    private Input minutesInput;

    @ElementInfo(name = "Time", findBy = @FindBy(id = "time-input"))
    private Input timeInput;

    public CountdownTimerDW(IParent parent) {
        super("Countdown timer dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='SET THE COUNTDOWN TIMER']]"));
    }

    public boolean isHoursValueEqual(int value, Duration timeout) {
        return hoursInput.waitForValueEquals(value, timeout);
    }

    public boolean isMinutesValueEqual(int value, Duration timeout) {
        return minutesInput.waitForValueEquals(value, timeout);
    }

    public void setTime(int hours, int minutes) {
        info(String.format("Set hours and minutes: '%s:%s'.", hours, minutes));
        timeInput.setText(hours + ":" + minutes);
    }
}
