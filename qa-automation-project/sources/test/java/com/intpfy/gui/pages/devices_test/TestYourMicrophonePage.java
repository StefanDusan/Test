package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TestYourMicrophonePage extends BaseDevicesTestPage {

    @ElementInfo(name = "Title", findBy = @FindBy(xpath = "//h1[contains(text(),'Test your microphone')]"))
    private Element title;

    TestYourMicrophonePage(IPageContext pageContext) {
        super("Test your microphone", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return title.visible(timeout);
    }

    @Override
    public RehearseYourVoiceRecordPage next() {
        info("Next.");
        clickNext();
        return new RehearseYourVoiceRecordPage(getPageContext());
    }
}
