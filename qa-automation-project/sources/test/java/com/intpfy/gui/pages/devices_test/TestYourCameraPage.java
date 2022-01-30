package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TestYourCameraPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Title", findBy = @FindBy(xpath = "//h1[contains(text(),'Test your camera')]"))
    private Element title;

    TestYourCameraPage(IPageContext pageContext) {
        super("Test your camera", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return title.visible(timeout);
    }

    @Override
    public TestYourMicrophonePage next() {
        info("Next.");
        clickNext();
        return new TestYourMicrophonePage(getPageContext());
    }
}
