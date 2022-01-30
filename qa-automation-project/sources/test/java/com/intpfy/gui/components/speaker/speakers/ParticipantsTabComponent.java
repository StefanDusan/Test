package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class ParticipantsTabComponent extends BaseComponent {

    private static final String ACTIVE_CLASS = "_active";

    public ParticipantsTabComponent(IParent parent) {
        super("Participants tab", parent, By.cssSelector("div.speakers-tab"));
    }

    public boolean isActive(Duration timeout) {
        return getComponentElement().waitCssClassContains(ACTIVE_CLASS, timeout);
    }
}
