package com.intpfy.gui.dialogs.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ChannelSwitchDW extends BaseComponent {

    @ElementInfo(name = "Timer", findBy = @FindBy(css= "div.countdown-modal__timer"))
    private Element timerElement;

    public ChannelSwitchDW(IParent parent) {
        super("Channel switch dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//div[text()='Time until the channel switch']]"));
    }

    public Duration getTimerDuration() {
        if (timerElement.waitForTextNotEmpty(WebSettings.AJAX_TIMEOUT)) {
            String[] timerTextSplit = timerElement.getText().split(":");
            int minutes = Integer.parseInt(timerTextSplit[0]);
            int seconds = Integer.parseInt(timerTextSplit[1]);
            return Duration.ofMinutes(minutes).plusSeconds(seconds);
        }
        throw new RuntimeException("Timer value is empty.");
    }
}
