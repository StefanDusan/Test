package com.intpfy.gui.components.common;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AutoVolumeComponent extends BaseComponent {

    @ElementInfo(name = "Enable Auto-volume complex checkbox", findBy = @FindBy(css = "div.toggle-switch"))
    private ComplexCheckbox enableCheckbox;

    public AutoVolumeComponent(IParent parent) {
        super("Auto-volume", parent, By.cssSelector("div.auto-volume-toggle"));
    }

    public boolean isAvailable(Duration timeout) {
        return enableCheckbox.visible(timeout);
    }

    public boolean isUnavailable(Duration timeout) {
        return enableCheckbox.notVisible(timeout);
    }

    public void enable() {
        enableCheckbox.select();
    }

    public void disable() {
        enableCheckbox.unselect();
    }

    public boolean isEnabled(Duration timeout) {
        return enableCheckbox.waitIsSelected(timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return enableCheckbox.waitIsNotSelected(timeout);
    }
}
