package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class CaptionsSettingsComponent extends BaseComponent {

    @ElementInfo(name = "'Captions access' complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='transcriptAccess']]"))
    private ComplexCheckbox enableAccessCheckbox;

    public CaptionsSettingsComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Captions settings", parent, By.xpath("./self::*"));
    }

    public void enableAccess() {
        enableAccessCheckbox.select();
    }

    public boolean isAccessEnabled(Duration timeout) {
        return enableAccessCheckbox.waitIsSelected(timeout);
    }
}
