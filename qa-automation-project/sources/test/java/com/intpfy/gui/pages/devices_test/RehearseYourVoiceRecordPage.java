package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class RehearseYourVoiceRecordPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Description", findBy = @FindBy(xpath = "//p[contains(text(),'Click on the Record Icon')]"))
    private Element description;

    RehearseYourVoiceRecordPage(IPageContext pageContext) {
        super("Rehearse your voice (record)", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return description.visible(timeout);
    }

    @Override
    public RehearseYourVoicePlayPage next() {
        info("Next.");
        clickNext();
        return new RehearseYourVoicePlayPage(getPageContext());
    }
}
