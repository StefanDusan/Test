package com.intpfy.gui.components.advanced_moderator;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.moderator.LanguageSessionComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class RecordingComponent extends LanguageSessionComponent {

    @ElementInfo(name = "Enable Recording complex checkbox", findBy = @FindBy(css = "div.toggle-switch"))
    private ComplexCheckbox enableCheckbox;

    @ElementInfo(name = "Recording status", findBy = @FindBy(css = "div.recording-status"))
    private Element statusElement;

    public RecordingComponent(IParent parent) {
        super("Recording", parent, By.xpath(".//div[contains(@class, 'control-header__recording')]"));
    }

    public void enable() {
        enableCheckbox.select();
    }

    public void disable() {
        enableCheckbox.unselect();
    }

    public boolean isEnabled(Duration timeout) {
        return enableCheckbox.waitIsSelected(timeout) && statusElement.waitForTextNotContains("off", timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return enableCheckbox.waitIsNotSelected(timeout) && statusElement.waitForTextContains("off", timeout);
    }
}
