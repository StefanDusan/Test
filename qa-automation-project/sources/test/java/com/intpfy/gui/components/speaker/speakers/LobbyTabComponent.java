package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LobbyTabComponent extends BaseComponent {

    private static final String ACTIVE_CLASS = "_active";

    @ElementInfo(name = "Speakers counter", findBy = @FindBy(css = "div.user-counter"))
    private Element speakersCounterElement;

    public LobbyTabComponent(IParent parent) {
        super("Lobby tab", parent, By.cssSelector("div.lobby-tab"));
    }

    public boolean isActive(Duration timeout) {
        return getComponentElement().waitCssClassContains(ACTIVE_CLASS, timeout);
    }

    public boolean isSpeakersCountEqual(int count, Duration timeout) {
        return speakersCounterElement.waitForTextEquals(String.valueOf(count), timeout);
    }
}
