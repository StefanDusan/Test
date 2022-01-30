package com.intpfy.gui.components.moderator;

import com.intpfy.gui.dialogs.moderator.CountdownTimerDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.annotations.Matcher;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TimerComponent extends BaseComponent {

    private static final String TIMER_ELEMENT_CLASS_NAME = "timer-display__digit";

    @ElementInfo(name = "Open", findBy = @FindBy(css = "i.timer-set"))
    private Button openButton;

    @ElementInfo(name = "Hours", findBy = @FindBy(className = TIMER_ELEMENT_CLASS_NAME), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "1"))
    private Element hoursElement;

    @ElementInfo(name = "Minutes", findBy = @FindBy(className = TIMER_ELEMENT_CLASS_NAME), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "2"))
    private Element minutesElement;

    @ElementInfo(name = "Seconds", findBy = @FindBy(className = TIMER_ELEMENT_CLASS_NAME), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "3"))
    private Element secondsElement;

    public TimerComponent(IParent parent) {
        super("Timer", parent, By.cssSelector("div.timer"));
    }

    public CountdownTimerDW openCountdownTimerDW() {
        openButton.click();
        return new CountdownTimerDW(getPage());
    }

    public boolean isHoursValueEqual(int value, Duration timeout) {
        return hoursElement.waitForValueEquals(value, timeout);
    }

    public boolean isMinutesValueEqual(int value, Duration timeout) {
        return minutesElement.waitForValueEquals(value, timeout);
    }

    public int getSeconds() {
        return Integer.parseInt(secondsElement.getText());
    }
}
