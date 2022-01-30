package com.intpfy.gui.pages.emi;

import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class CalendarPage extends BaseEmiPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//span[contains(text(),'Events calendar')]"))
    private Element pageTitle;

    public CalendarPage(IPageContext pageContext) {
        super("Calendar page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }
}
