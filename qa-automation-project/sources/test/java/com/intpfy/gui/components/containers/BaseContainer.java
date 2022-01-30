package com.intpfy.gui.components.containers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;

import java.time.Duration;

public abstract class BaseContainer extends BaseComponent {

    protected BaseContainer(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    public boolean isActive(Duration timeout) {
        return createActiveConferenceBox().visible(timeout);
    }

    public boolean isInactive(Duration timeout) {
        return createActiveConferenceBox().notVisible(timeout);
    }

    private Element createActiveConferenceBox() {
        String name = "Conference box";
        By locator = By.xpath("./ancestor::div[@id='conference-active-box']");
        return getComponentElement().createChild(name, locator);
    }
}
