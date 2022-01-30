package com.intpfy.gui.components.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class FullscreenComponent extends BaseComponent {

    @ElementInfo(name = "Full screen", findBy = @FindBy(id = "fs-btn"))
    private Button fullscreenButton;

    @ElementInfo(name = "Icon", findBy = @FindBy(css = "svg.icon"))
    private Element iconElement;

    public FullscreenComponent(IParent parent) {
        super("Fullscreen", parent, By.cssSelector("li._app-full-screen"));
    }

    public void enable() {
        if (isDisabled(Duration.ZERO)) {
            fullscreenButton.click();
            isEnabled();
        }
    }

    public void disable() {
        if (isEnabled(Duration.ZERO)) {
            fullscreenButton.click();
            isDisabled();
        }
    }

    public boolean isEnabled() {
        return isEnabled(WebSettings.AJAX_TIMEOUT);
    }

    public boolean isEnabled(Duration timeout) {
        return iconElement.waitCssClassContains("fullscreen", timeout);
    }

    public boolean isDisabled() {
        return isDisabled(WebSettings.AJAX_TIMEOUT);
    }

    private boolean isDisabled(Duration timeout) {
        return iconElement.waitCssClassNotContains("fullscreen", timeout);
    }
}