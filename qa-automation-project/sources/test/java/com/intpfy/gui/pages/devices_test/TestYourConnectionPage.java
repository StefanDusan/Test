package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TestYourConnectionPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Title", findBy = @FindBy(xpath = "//h1[contains(text(),'Test your connection')]"))
    private Element title;

    TestYourConnectionPage(IPageContext pageContext) {
        super("Test your connection", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return title.visible(timeout);
    }

    @Override
    public FinishedPage next() {
        info("Next.");
        clickNext();
        return new FinishedPage(getPageContext());
    }
}
