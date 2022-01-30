package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class RehearseYourVoicePlayPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Description", findBy = @FindBy(xpath = "//p[contains(text(),'Click on the Play Icon')]"))
    private Element description;

    RehearseYourVoicePlayPage(IPageContext pageContext) {
        super("Rehearse your voice (play)", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return description.visible(timeout);
    }

    @Override
    public TestYourConnectionPage next() {
        info("Next.");
        clickNext();
        return new TestYourConnectionPage(getPageContext());
    }
}
