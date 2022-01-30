package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class StepByStepPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Title", findBy = @FindBy(xpath = "//h1[contains(text(),'Step by step instructions to perform a test call')]"))
    private Element title;

    public StepByStepPage(IPageContext pageContext) {
        super("Step by step", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return title.visible(timeout);
    }

    @Override
    public TestYourCameraPage next() {
        info("Next.");
        clickNext();
        return new TestYourCameraPage(getPageContext());
    }
}
